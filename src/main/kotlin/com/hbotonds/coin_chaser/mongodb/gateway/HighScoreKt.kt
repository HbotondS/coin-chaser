package com.hbotonds.coin_chaser.mongodb.gateway

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class HighScoreKt {
    lateinit var id: ObjectId

    @BsonProperty(value = "name")
    lateinit var name: String

    @BsonProperty(value = "score")
    var score: Int = 0
}