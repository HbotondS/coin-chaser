package com.hbotonds.coin_chaser.mongodb.gateway.codec

import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import org.bson.BsonReader
import org.bson.BsonWriter
import org.bson.codecs.Codec
import org.bson.codecs.DecoderContext
import org.bson.codecs.EncoderContext
import org.bson.codecs.configuration.CodecRegistry

class HighScoreCodecKt(codecRegistry: CodecRegistry): Codec<HighScoreKt> {
    private val codecRegistry: CodecRegistry

    init {
        this.codecRegistry = codecRegistry
    }

    override fun encode(writer: BsonWriter, highScore: HighScoreKt, encoderContext: EncoderContext?) {
        writer.writeStartDocument()
        writer.writeName("_id")
        writer.writeObjectId(highScore.id)
        writer.writeName("name")
        writer.writeString(highScore.name)
        writer.writeName("score")
        writer.writeInt32(highScore.score)
        writer.writeEndDocument()
    }

    override fun getEncoderClass(): Class<HighScoreKt> {
        return HighScoreKt::class.java
    }

    override fun decode(reader: BsonReader, decoderContext: DecoderContext?): HighScoreKt {
        val highScore = HighScoreKt()

        reader.readStartDocument()
        highScore.id = reader.readObjectId()
        highScore.name = reader.readString("name")
        highScore.score = reader.readInt32("score")
        reader.readEndDocument()

        return highScore
    }
}