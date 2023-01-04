package com.HbotondS.coin_chaser;

import com.almasb.fxgl.dsl.EntityBuilder;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class MapFactory implements EntityFactory {

    @Spawns("map")
    public Entity newMap(SpawnData data) {
        return entityBuilder(data)
                .bbox(new HitBox(BoundingShape.box(data.get("width"), data.get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
}
