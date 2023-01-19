package com.hbotonds.coin_chaser.mongodb.gateway

import com.almasb.fxgl.logging.Logger
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Sorts.descending

class HighScoreGatewayKt(collection: MongoCollection<HighScoreKt>) {
    private val collection: MongoCollection<HighScoreKt>
    private val logger: Logger = Logger.get(HighScoreGatewayKt::class.java)

    init {
        this.collection = collection
    }

    fun printOne() {
        val highScore = collection.find(eq("name", "Boti")).first();
        logger.info(highScore.toString())
    }

    fun insertOne(highScore: HighScoreKt) {
        logger.infof("Save high score", highScore.toString())
        collection.insertOne(highScore)
    }

    fun findTopScores(): FindIterable<HighScoreKt> {
        val highScores = collection.find()
            .sort(descending("score"))
            .limit(5)
        logger.info("Getting top 5 scores")
        highScores.forEach { highScore -> logger.info(highScore.toString()) }

        return highScores
    }
}