package com.hbotonds.coin_chaser.mongodb.gateway.codec;

import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class MyCodecProvider implements CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> myClass, CodecRegistry registry) {
        if (myClass == HighScore.class) {
            return (Codec<T>) new HighScoreCodec(registry);
        }
        return null;
    }
}
