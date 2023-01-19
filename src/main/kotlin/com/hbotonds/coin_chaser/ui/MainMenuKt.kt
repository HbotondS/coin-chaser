package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.MenuType
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.dsl.texture
import com.hbotonds.coin_chaser.MainKt.Companion.TILE_LENGTH
import com.hbotonds.coin_chaser.main
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

class MainMenuKt : FXGLMenu(MenuType.MAIN_MENU) {
    private var inScoresMenu: Boolean = false
    private lateinit var scoresMenu: Pane
    private var mainMenuBtns: VBox

    init {
        val btnStart = TextButtonKt("Play", this::fireNewGame)
        val btnOptions = TextButtonKt("Options") { }
        val btnTopScores = TextButtonKt("Top Scores", this::toggleHighScores)
        val btnExit = TextButtonKt("Exit", this::fireExit)

        mainMenuBtns = VBox(15.0,
                btnStart,
                btnOptions,
                btnTopScores,
                btnExit
        )
        mainMenuBtns.translateX = TILE_LENGTH.toDouble()
        mainMenuBtns.translateY = (9 * TILE_LENGTH + 40).toDouble()

        contentRoot.children.addAll(createBackground(), createLogo(), mainMenuBtns)
    }

    private fun toggleHighScores() {
        if (!inScoresMenu) {
            scoresMenu = TopScoreMenuKt(this::toggleHighScores).topScoreMenu
            contentRoot.children.remove(mainMenuBtns)
            contentRoot.children.addAll(scoresMenu)
        } else {
            contentRoot.children.remove(scoresMenu)
            contentRoot.children.add(mainMenuBtns)
        }
        inScoresMenu = !inScoresMenu
    }

    private fun createBackground(): Node {
        val bg = texture("background/Background.png").image
        val iv = ImageView()
        iv.image = bg
        iv.fitHeight = getAppHeight().toDouble()
        iv.fitWidth = getAppWidth().toDouble()
        return iv
    }

    private fun createLogo(): Node {
        val logo = texture("logo/coin-chaser-logo.png").image
        val iv = ImageView(logo)
        iv.translateX = TILE_LENGTH * 11.0
        iv.translateY = TILE_LENGTH.toDouble()
        return iv
    }
}