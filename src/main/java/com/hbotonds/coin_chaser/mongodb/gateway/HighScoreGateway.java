package com.hbotonds.coin_chaser.mongodb.gateway;

import com.almasb.fxgl.logging.Logger;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Sorts.descending;

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

    public FindIterable<HighScore> findTopScores() {
        var highScore = collection.find()
                .sort(descending("score"))
                .limit(5);
        logger.info("Getting top 5 score");
        highScore.forEach(o -> logger.info(o.toString()));
        return highScore;
    }
}
