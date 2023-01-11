package com.hbotonds.coin_chaser

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.dsl.FXGL.Companion.setLevelFromMap
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.virtual.VirtualButton
import com.almasb.fxgl.logging.Logger
import javafx.scene.input.KeyCode

class MainKt: GameApplication() {
    private var player: Entity? = null
    private val logger: Logger = Logger.get(MainKt::class.java)

    override fun initSettings(settings: GameSettings?) {
        settings?.width = 30 * 128
        settings?.height = 15 * 128

        settings!!.title = "Coin Chaser"
        settings.version = "1.0-SNAPSHOT"
        settings.isMainMenuEnabled = true
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(CoinChaserFactoryKt())
        setLevelFromMap("tmx/map.tmx")

        player = getGameWorld().spawn("player", 100.0, 1536.0)
        CoinSpawnerKt.spawnNewCoin()
    }

    override fun initPhysics() {
        getPhysicsWorld().setGravity(0.0, 1000.0)

        onCollisionBegin(EntityTypeKt.PLAYER, EntityTypeKt.COIN) { _, coin ->
            coin.removeFromWorld()
            while (true) {
                val newCoin = CoinSpawnerKt.spawnNewCoin()
                if (coin.position.equals(newCoin.position)) {
                    newCoin.removeFromWorld()
                } else {
                    break
                }
            }
            logger.info("collision with coin")
        }
    }

    override fun initInput() {
        getInput().addAction(MyInputActionKt.Builder("left")
                .setOnAction { player?.getComponent(PlayerComponentKt::class.java)?.left() }
                .setOnActionEnd { player?.getComponent(PlayerComponentKt::class.java)?.stop() }
                .build(), KeyCode.A, VirtualButton.LEFT)

        getInput().addAction(MyInputActionKt.Builder("right")
                .setOnAction { player?.getComponent(PlayerComponentKt::class.java)?.right() }
                .setOnActionEnd { player?.getComponent(PlayerComponentKt::class.java)?.stop() }
                .build(), KeyCode.D, VirtualButton.RIGHT)

        getInput().addAction(MyInputActionKt.Builder("jump")
                .setOnAction { player?.getComponent(PlayerComponentKt::class.java)?.jump() }
                .build(), KeyCode.W, VirtualButton.UP)
    }
}

fun main(args: Array<String>) {
    GameApplication.launch(MainKt::class.java, args)
}