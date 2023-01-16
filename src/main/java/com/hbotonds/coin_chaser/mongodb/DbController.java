package com.hbotonds.coin_chaser.mongodb;

import com.almasb.fxgl.logging.Logger;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreGateway;
import com.hbotonds.coin_chaser.mongodb.gateway.codec.MyCodecProvider;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt32;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DbController {
    private static final Logger logger = Logger.get(DbController.class);
    private MongoDatabase database;
    private MongoClient client;

    public DbController() {
        this.connect();
    }

    private void connect() {
        var connectionString = new ConnectionString("mongodb://localhost:6969/");
        var codecRegistry = fromRegistries(
                fromProviders(new MyCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
        );
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        try {
            client = MongoClients.create(clientSettings);
            database = client.getDatabase("coin_chaser");
            logger.info("Connected successfully to server.");
        } catch (Exception e) {
            logger.fatal("An error occurred while attempting to connect to server: ", e);
        }
    }

    public MongoCollection<HighScore> getCollection() {
        return database.getCollection("high_scores", HighScore.class);
    }
}
