package com.hbotonds.coin_chaser

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.dsl.*
import com.almasb.fxgl.dsl.FXGL.Companion.setLevelFromMap
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.virtual.VirtualButton
import com.almasb.fxgl.logging.Logger
import com.hbotonds.coin_chaser.observer.CoinCollectedKt
import com.hbotonds.coin_chaser.observer.NextLevelKt
import com.hbotonds.coin_chaser.observer.ScoreKt
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color

class MainKt: GameApplication() {
    private lateinit var player: Entity
    private val logger: Logger = Logger.get(MainKt::class.java)
    private lateinit var coinCollected: CoinCollectedKt

    private val TILE_LENGTH = 128
    private val APP_HEIGHT = 15 * TILE_LENGTH
    private val APP_WIDTH = 30 * TILE_LENGTH

    override fun initSettings(settings: GameSettings) {
        settings.width = APP_WIDTH
        settings.height = APP_HEIGHT

        settings.title = "Coin Chaser"
        settings.version = "1.0-SNAPSHOT"
        settings.isMainMenuEnabled = true
    }

    override fun initGameVars(vars: MutableMap<String, Any>) {
        vars["score"] = 0
        vars["coins2NextLvl"] = 3
    }

    override fun initGame() {
        getGameWorld().addEntityFactory(CoinChaserFactoryKt())
        setLevelFromMap("tmx/map.tmx")
        spawn("background")

        player = getGameWorld().spawn("player", 100.0, 1536.0)
        CoinSpawnerKt.spawnNewCoin()

        eventBuilder()
                .`when` { player.position.y > getGameScene().appHeight }
                .thenRun {
                    getDialogService().showMessageBox("Game over.") {
                        getGameController().exit()
                    }
                }
                .buildAndStart()

        coinCollected = CoinCollectedKt()
        coinCollected.addObserver(NextLevelKt())
        coinCollected.addObserver(ScoreKt())
    }

    override fun initPhysics() {
        getPhysicsWorld().setGravity(0.0, 1000.0)

        onCollisionBegin(EntityTypeKt.PLAYER, EntityTypeKt.COIN) { _, coin ->
            coinCollected.notifyObservers()
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
                .setOnAction { player.getComponent(PlayerComponentKt::class.java)?.left() }
                .setOnActionEnd { player.getComponent(PlayerComponentKt::class.java)?.stop() }
                .build(), KeyCode.A, VirtualButton.LEFT)

        getInput().addAction(MyInputActionKt.Builder("right")
                .setOnAction { player.getComponent(PlayerComponentKt::class.java)?.right() }
                .setOnActionEnd { player.getComponent(PlayerComponentKt::class.java)?.stop() }
                .build(), KeyCode.D, VirtualButton.RIGHT)

        getInput().addAction(MyInputActionKt.Builder("jump")
                .setOnAction { player.getComponent(PlayerComponentKt::class.java)?.jump() }
                .build(), KeyCode.W, VirtualButton.UP)
    }

    override fun initUI() {
        val scoreTxt = getUIFactoryService().newText("", Color.BLACK, 30.0)
        scoreTxt.textProperty().bind(getip("score").asString())
        scoreTxt.translateX = (APP_WIDTH - 30).toDouble()
        scoreTxt.translateY = 30.0
        scoreTxt.stroke = Color.BLACK

        val coins2NextLvlTxt = getUIFactoryService().newText("", Color.BLACK, 30.0)
        coins2NextLvlTxt
                .textProperty()
                .bind(getip("coins2NextLvl").asString("Coins required for the next level: %d"))

        coins2NextLvlTxt.translateX = (APP_WIDTH - 4 * TILE_LENGTH).toDouble()
        coins2NextLvlTxt.translateY = (APP_HEIGHT - 20).toDouble()
        coins2NextLvlTxt.stroke = Color.BLACK

        getGameScene().addUINodes(scoreTxt, coins2NextLvlTxt)
    }
}

fun main(args: Array<String>) {
    GameApplication.launch(MainKt::class.java, args)
}