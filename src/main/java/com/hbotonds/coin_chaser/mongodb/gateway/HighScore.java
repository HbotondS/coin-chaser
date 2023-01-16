package com.hbotonds.coin_chaser.mongodb.gateway;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HighScore {
    private ObjectId id;

    @BsonProperty(value = "name")
    private String name;

    @BsonProperty(value = "score")
    private int score;
}
