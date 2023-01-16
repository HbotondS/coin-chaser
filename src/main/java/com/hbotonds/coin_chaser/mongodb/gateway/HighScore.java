package com.hbotonds.coin_chaser.mongodb.gateway;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
public class HighScore {
    private ObjectId id;

    @BsonProperty(value = "name")
    private String name;

    @BsonProperty(value = "score")
    private int score;
}
