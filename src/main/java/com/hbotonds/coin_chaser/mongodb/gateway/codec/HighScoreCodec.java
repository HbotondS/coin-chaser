package com.hbotonds.coin_chaser.mongodb.gateway.codec;

import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class HighScoreCodec implements Codec<HighScore> {
    private final CodecRegistry codecRegistry;

    public HighScoreCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public HighScore decode(BsonReader bsonReader, DecoderContext decoderContext) {
        var highScore = new HighScore();

        bsonReader.readStartDocument();
        highScore.setId(bsonReader.readObjectId());
        highScore.setName(bsonReader.readString("name"));
        highScore.setScore(bsonReader.readInt32("score"));
        bsonReader.readEndDocument();

        return highScore;
    }

    @Override
    public void encode(BsonWriter bsonWriter, HighScore highScore, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeName("_id");
        bsonWriter.writeObjectId(highScore.getId());
        bsonWriter.writeName("name");
        bsonWriter.writeString(highScore.getName());
        bsonWriter.writeName("score");
        bsonWriter.writeInt32(highScore.getScore());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<HighScore> getEncoderClass() {
        return HighScore.class;
    }
}
