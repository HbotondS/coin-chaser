package com.hbotonds.coin_chaser.mongodb.gateway

import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

class HighScoreKt {
    constructor() : this(ObjectId.get(), "", 0)

    constructor(id: ObjectId, name: String, score: Int) {
        this.id = id
        this.name = name
        this.score = score
    }

    var id: ObjectId

    @BsonProperty(value = "name")
    var name: String

    @BsonProperty(value = "score")
    var score: Int = 0
}