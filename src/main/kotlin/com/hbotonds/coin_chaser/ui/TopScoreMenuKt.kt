package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.dsl.getUIFactoryService
import com.almasb.fxgl.logging.Logger
import com.hbotonds.coin_chaser.Main
import com.hbotonds.coin_chaser.MainKt
import com.hbotonds.coin_chaser.MainKt.Companion.TILE_LENGTH
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import com.mongodb.client.FindIterable
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text

class TopScoreMenuKt(toggleHighScores: Runnable) {
    val topScoreMenu = Pane()
    private val toggleHighScores: Runnable
    private val logger = Logger.get(TopScoreMenuKt::class.java)

    init {
        this.toggleHighScores = toggleHighScores

        val namesTxtList = VBox(15.0)
        val scoresTxtList = VBox(15.0)
        val topScoreList = createTopScoreList()
        topScoreList?.forEach { highScore ->
            namesTxtList.children.add(
                getUIFactoryService().newText(highScore.name, Color.GRAY, 50.0)
            )
            scoresTxtList.children.add(
                getUIFactoryService().newText(highScore.score.toString(), Color.GRAY, 50.0)
            )
        }

        val highScoreTxtList = HBox(
            200.0,
            namesTxtList,
            scoresTxtList
        )
        highScoreTxtList.translateX = 13.0 * TILE_LENGTH
        highScoreTxtList.translateY = 5.0 * TILE_LENGTH

        this.topScoreMenu.children.addAll(
            highScoreTxtList,
            createMenuTitle(),
            createBackBtn()
        )
    }

    private fun createMenuTitle(): Text {
        val title = getUIFactoryService().newText("Top Scores", Color.GRAY, 60.0)
        title.translateX = 14 * TILE_LENGTH - 70.0
        title.translateY = 4.0 * TILE_LENGTH

        return title
    }

    private fun createTopScoreList(): FindIterable<HighScoreKt>? {
        try {
            return MainKt.gateway.findTopScores()
        } catch (e: Exception) {
            logger.fatal("An error occurred while attempting to get top score:", e)
            return null
        }
    }

    private fun createBackBtn(): TextButtonKt {
        val backBtn = TextButtonKt("Back", toggleHighScores)
        backBtn.translateX = Main.TILE_LENGTH.toDouble()
        backBtn.translateY = (11 * Main.TILE_LENGTH + 10).toDouble()

        return backBtn
    }
}