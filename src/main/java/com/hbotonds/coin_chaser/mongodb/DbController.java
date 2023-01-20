package com.hbotonds.coin_chaser.mongodb;

import com.almasb.fxgl.logging.Logger;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreGateway;
import com.hbotonds.coin_chaser.mongodb.gateway.codec.MyCodecProvider;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.MongoServerUnavailableException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt32;

import java.util.concurrent.TimeUnit;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DbController {
    private static final Logger logger = Logger.get(DbController.class);
    private MongoDatabase database;
    private MongoClient client;
    private boolean connectionSuccessful;

    private static DbController instance;

    private DbController() {
        this.connect();
    }

    public static DbController getInstance() {
        if (instance == null) {
            instance = new DbController();
        }

        return instance;
    }


    private void connect() {
        var connectionString = new ConnectionString("mongodb://localhost:6969/");
        var codecRegistry = fromRegistries(
                fromProviders(new MyCodecProvider()),
                MongoClientSettings.getDefaultCodecRegistry()
        );
        MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applyToClusterSettings(builder -> builder.serverSelectionTimeout(10000, TimeUnit.MILLISECONDS))
                .codecRegistry(codecRegistry)
                .build();

        client = MongoClients.create(clientSettings);
        database = client.getDatabase("coin_chaser");
        try {
            var command = new BsonDocument("ping", new BsonInt32(1));
            database.runCommand(command);
            logger.info("Connected successfully to server.");
            this.connectionSuccessful = true;
        } catch (MongoException e) {
            logger.fatal("An error occurred while attempting to connect to server: ", e);
            this.connectionSuccessful = false;
        }
    }

    public MongoCollection<HighScore> getCollection() {
        if (connectionSuccessful) {
            return database.getCollection("high_scores", HighScore.class);
        } else {
            throw new MongoServerUnavailableException("Unable to connect to server");
        }
    }
}
