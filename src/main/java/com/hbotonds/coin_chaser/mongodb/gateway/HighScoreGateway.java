package com.hbotonds.coin_chaser.mongodb.gateway;

import com.almasb.fxgl.logging.Logger;
import com.mongodb.client.MongoCollection;

public class HighScoreGateway {

    private final MongoCollection<HighScore> collection;
    private final Logger logger = Logger.get(HighScoreGateway.class);

    public HighScoreGateway(MongoCollection<HighScore> collection) {
        this.collection = collection;
    }

    public HighScore findOne() {
        var highScore = collection.find().first();
        logger.info(highScore.toString());
        return highScore;
    }

    public void insertOne(HighScore highScore) {
        logger.infof("Save high score", highScore.toString());
        collection.insertOne(highScore);
    }
}
