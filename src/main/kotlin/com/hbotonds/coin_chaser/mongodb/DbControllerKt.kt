package com.hbotonds.coin_chaser.mongodb

import com.almasb.fxgl.logging.Logger
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import com.hbotonds.coin_chaser.mongodb.gateway.codec.MyCodecProviderKt
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.MongoServerUnavailableException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.BsonDocument
import org.bson.BsonInt32
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import java.util.concurrent.TimeUnit

class BbControllerKt {
    private val logger = Logger.get(BbControllerKt::class.java)
    private lateinit var database: MongoDatabase
    private lateinit var client: MongoClient
    private var connectionSuccessful: Boolean = false

    init {
        this.connect()
    }

    private fun connect() {
        val connectionString = ConnectionString("mongodb://localhost:6969/")
        val codecRegistry = fromRegistries(
            fromProviders(MyCodecProviderKt()),
            MongoClientSettings.getDefaultCodecRegistry()
        )
        val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .applyToClusterSettings { builder -> builder.serverSelectionTimeout(10000, TimeUnit.MILLISECONDS) }
            .codecRegistry(codecRegistry)
            .build()

        client = MongoClients.create(clientSettings)
        database = client.getDatabase("coin_chaser")
        try {
            val command = BsonDocument("ping", BsonInt32(1))
            database.runCommand(command)
            logger.info("Connected successfully to server.")
            this.connectionSuccessful = true
        } catch (e: MongoException) {
            logger.fatal("An error occurred while attempting to connect to server: ", e)
            connectionSuccessful = false
        }
    }

    fun getCollection(): MongoCollection<HighScoreKt> {
        if (connectionSuccessful) {
            return database.getCollection("high_scores", HighScoreKt::class.java)
        } else {
            throw MongoServerUnavailableException("Unable to connect to server")
        }
    }
}
