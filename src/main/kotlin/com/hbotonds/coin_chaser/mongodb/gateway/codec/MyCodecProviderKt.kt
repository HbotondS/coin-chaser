package com.hbotonds.coin_chaser.mongodb.gateway.codec

import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import org.bson.codecs.Codec
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistry

class MyCodecProviderKt: CodecProvider {
    override fun <T : Any?> get(myClass: Class<T>?, registry: CodecRegistry): Codec<T>? {
        if (myClass == HighScoreKt::class.java) {
            return HighScoreCodecKt(registry) as Codec<T>
        }
        return null
    }
}