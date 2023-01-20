package com.hbotonds.coin_chaser

import com.almasb.fxgl.app.GameApplication
import com.almasb.fxgl.app.GameSettings
import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.SceneFactory
import com.almasb.fxgl.core.util.InputPredicates
import com.almasb.fxgl.dsl.FXGL.Companion.geti
import com.almasb.fxgl.dsl.FXGL.Companion.setLevelFromMap
import com.almasb.fxgl.dsl.eventBuilder
import com.almasb.fxgl.dsl.getDialogService
import com.almasb.fxgl.dsl.getGameController
import com.almasb.fxgl.dsl.getGameScene
import com.almasb.fxgl.dsl.getGameWorld
import com.almasb.fxgl.dsl.getInput
import com.almasb.fxgl.dsl.getPhysicsWorld
import com.almasb.fxgl.dsl.getUIFactoryService
import com.almasb.fxgl.dsl.getip
import com.almasb.fxgl.dsl.onCollisionBegin
import com.almasb.fxgl.dsl.spawn
import com.almasb.fxgl.entity.Entity
import com.almasb.fxgl.input.virtual.VirtualButton
import com.almasb.fxgl.logging.Logger
import com.almasb.fxgl.ui.DialogFactoryService
import com.hbotonds.coin_chaser.mongodb.DbControllerKt
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreGatewayKt
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import com.hbotonds.coin_chaser.observer.CoinCollectedKt
import com.hbotonds.coin_chaser.observer.NextLevelKt
import com.hbotonds.coin_chaser.observer.ScoreKt
import com.hbotonds.coin_chaser.ui.MainMenuKt
import com.hbotonds.coin_chaser.ui.dialog.CustomDialogFactoryServiceKt
import com.mongodb.MongoServerUnavailableException
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import org.bson.types.ObjectId
import kotlin.collections.set
import kotlin.concurrent.thread

class CoinChaserAppKt : GameApplication() {
    private lateinit var player: Entity
    private val logger: Logger = Logger.get(CoinChaserAppKt::class.java)
    private lateinit var coinCollected: CoinCollectedKt

    companion object {
        @JvmStatic
        val TILE_LENGTH = 128

        @JvmStatic
        lateinit var gateway: HighScoreGatewayKt
    }

    private val APP_HEIGHT = 15 * TILE_LENGTH
    private val APP_WIDTH = 30 * TILE_LENGTH

    override fun initSettings(settings: GameSettings) {
        settings.width = APP_WIDTH
        settings.height = APP_HEIGHT

        settings.title = "Coin Chaser"
        settings.version = "1.0-SNAPSHOT"
        settings.isMainMenuEnabled = true

        settings.setEngineServiceProvider(DialogFactoryService::class.java, CustomDialogFactoryServiceKt::class.java)

        settings.sceneFactory = object : SceneFactory() {
            override fun newMainMenu(): FXGLMenu {
                return MainMenuKt()
            }
        }
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
                    getDialogService().showInputBox(
                        "Please enter your name.",
                        InputPredicates.ALPHANUM
                    ) { name: String ->
                        val highScore = HighScoreKt(
                            ObjectId.get(),
                            name,
                            geti("score")
                        )
                        gateway.insertOne(highScore)
                        getGameController().exit()
                    }
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
            coin.removeFromWorld()
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
    thread(start = true) {
        try {
            CoinChaserAppKt.gateway = HighScoreGatewayKt(DbControllerKt.getCollection())
        } catch (e: MongoServerUnavailableException) {
            e.printStackTrace()
        }
    }
    GameApplication.launch(CoinChaserAppKt::class.java, args)
}