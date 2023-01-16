package com.hbotonds.coin_chaser.mongodb.gateway;

import com.almasb.fxgl.logging.Logger;
import com.hbotonds.coin_chaser.mongodb.DbController;
import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.eq;

public class HighScoreGateway {

    private final MongoCollection<HighScore> collection;
    private final Logger logger = Logger.get(HighScoreGateway.class);

    public HighScoreGateway() {
        this.collection = DbController.getCollection();
    }

    public void printOne() {
        var highScore = collection.find(eq("name", "Boti")).first();
        logger.info(highScore.toString());
    }
}
