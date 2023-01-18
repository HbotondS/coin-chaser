package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.dsl.getUIFactoryService
import com.almasb.fxgl.logging.Logger
import com.hbotonds.coin_chaser.MainKt.Companion.TILE_LENGTH
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreKt
import com.mongodb.client.FindIterable
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Text

class TopScoreMenuKt(toggleHighScores: Runnable) {
    private val topScoreMenu = Pane()
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
        TODO("Not yet implemented")
    }

    private fun createTopScoreList(): FindIterable<HighScoreKt>? {
        TODO("Not yet implemented")
    }

    private fun createBackBtn(): TextButtonKt {
        TODO("Not yet implemented")
    }
}