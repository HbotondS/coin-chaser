package com.hbotonds.coin_chaser.mongodb.gateway.codec;

import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class HighScoreCodec implements Codec<HighScore> {
    private CodecRegistry codecRegistry;

    public HighScoreCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public HighScore decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        var id = bsonReader.readObjectId();
        var name = bsonReader.readString("name");
        var score = bsonReader.readInt32("score");
        bsonReader.readEndDocument();

        var highScore = new HighScore();
        highScore.setId(id);
        highScore.setName(name);
        highScore.setScore(score);
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
