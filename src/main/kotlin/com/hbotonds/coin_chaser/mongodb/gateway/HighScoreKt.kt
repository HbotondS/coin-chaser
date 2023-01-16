package com.hbotonds.coin_chaser.mongodb.gateway

import lombok.Getter
import lombok.Setter
import lombok.ToString
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

@Getter
@Setter
@ToString
class HighScoreKt {
    private lateinit var id: ObjectId

    @BsonProperty(value = "name")
    private lateinit var name: String

    @BsonProperty(value = "score")
    private var score: Int = 0
}