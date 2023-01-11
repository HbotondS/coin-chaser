package com.hbotonds.coin_chaser

import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.entity.Entity
import javafx.geometry.Point2D

class CoinSpawnerKt {
    companion object {
        private val positions: ArrayList<Point2D> = object : ArrayList<Point2D>() {
            init {
                add(Point2D(256.0, 1536.0))
                add(Point2D(1165.0, 780.0))
                add(Point2D(1280.0, 1410.0))
                add(Point2D(1920.0, 1030.0))
                add(Point2D(2692.0, 1286.0))
            }
        }

        @JvmStatic
        fun spawnNewCoin(): Entity {
            val position = positions[(Math.random() * positions.size).toInt()]
            return getGameWorld().spawn("coin", position)
        }
    }
}