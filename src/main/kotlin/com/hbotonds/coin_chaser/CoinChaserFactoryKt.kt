package com.hbotonds.coin_chaser

import com.almasb.fxgl.dsl.entityBuilder
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.entity.EntityFactory
import com.almasb.fxgl.entity.SpawnData
import com.almasb.fxgl.entity.Spawns
import com.almasb.fxgl.entity.components.CollidableComponent
import com.almasb.fxgl.physics.BoundingShape
import com.almasb.fxgl.physics.HitBox
import com.almasb.fxgl.physics.PhysicsComponent
import com.almasb.fxgl.physics.box2d.dynamics.BodyType
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef
import javafx.geometry.Point2D
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Rectangle

class CoinChaserFactoryKt: EntityFactory {

    @Spawns("platform")
    fun newPlatform(data: SpawnData): Entity {
        return entityBuilder(data)
                .type(EntityTypeKt.PLATFORM)
                .bbox(HitBox(BoundingShape.box(data.get("width"), data.get("height"))))
                .with(PhysicsComponent())
                .build()
    }

    @Spawns("player")
    fun newPlayer(data: SpawnData): Entity {
        val physics = PhysicsComponent()
        physics.setBodyType(BodyType.DYNAMIC)

        physics.addGroundSensor(HitBox("GROUND_SENSOR", Point2D(50.0, 50.0), BoundingShape.box(20.0, 50.0)))
        // this avoids player sticking to walls
        physics.setFixtureDef(FixtureDef().friction(0.0F))

        return entityBuilder(data)
                .type(EntityTypeKt.PLAYER)
                .viewWithBBox(Rectangle(100.0, 100.0, Color.BLUE))
                .with(physics)
                .with(PlayerComponentKt())
                .with(CollidableComponent(true))
                .build()
    }

    @Spawns("coin")
    fun newCoin(data: SpawnData): Entity {
        return entityBuilder(data)
                .type(EntityTypeKt.COIN)
                .viewWithBBox(Circle(60.0, 60.0, 60.0, Color.GOLD))
                .with(CollidableComponent(true))
                .build()
    }
}