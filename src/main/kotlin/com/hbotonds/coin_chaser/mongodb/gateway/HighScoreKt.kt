package com.hbotonds.coin_chaser.mongodb.gateway

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class HighScoreKt(id: ObjectId, name: String, score: Int) {
    constructor() : this(ObjectId.get(), "", 0)

    lateinit var id: ObjectId

    @BsonProperty(value = "name")
    lateinit var name: String

    @BsonProperty(value = "score")
    var score: Int = 0
}