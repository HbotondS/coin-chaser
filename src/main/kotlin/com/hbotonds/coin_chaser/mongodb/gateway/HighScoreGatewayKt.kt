package com.hbotonds.coin_chaser.mongodb.gateway

import com.almasb.fxgl.logging.Logger
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq

class HighScoreGatewayKt() {
    private val collection: MongoCollection<HighScoreKt>
    private val logger: Logger = Logger.get(HighScoreGatewayKt::class.java)

    init {
        this.collection = DbController.getCollection()
    }

    fun printOne() {
        val highScore = collection.find(eq("name", "Boti")).first();
        logger.info(highScore.toString())
    }
}