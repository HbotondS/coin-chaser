package com.hbotonds.coin_chaser

import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.PhysicsComponent

class PlayerComponentKt: Component() {
    private val physics: PhysicsComponent = PhysicsComponent()
    private val speed = 400.0

    fun left() {
        physics.velocityX = -speed
    }

    fun right() {
        physics.velocityX = speed
    }

    fun stop() {
        physics.velocityX = 0.0
    }

    fun jump() {
        if (physics.isOnGround) {
            physics.velocityY = -1000.0
        }
    }
}