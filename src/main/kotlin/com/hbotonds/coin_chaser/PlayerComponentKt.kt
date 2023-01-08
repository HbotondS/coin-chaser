package com.hbotonds.coin_chaser

import com.almasb.fxgl.entity.component.Component
import com.almasb.fxgl.physics.PhysicsComponent

class PlayerComponentKt: Component() {
    private val physics: PhysicsComponent = PhysicsComponent()
    private var jumps: Boolean = false

    override fun onAdded() {
        physics.onGroundProperty().addListener { _, _, isOnGround ->
            if (isOnGround) {
                jumps = true
            }
        }
    }

    fun left() {
        physics.velocityX = -400.0
    }

    fun right() {
        physics.velocityX = 400.0
    }

    fun stop() {
        physics.velocityX = 0.0
    }

    fun jump() {
        if (jumps) {
            return
        }
        physics.velocityY = -1000.0
        jumps = true
    }
}