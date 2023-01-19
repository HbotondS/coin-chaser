package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.MenuType
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.dsl.texture
import com.hbotonds.coin_chaser.CoinChaserAppKt.Companion.TILE_LENGTH
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox

class MainMenuKt : FXGLMenu(MenuType.MAIN_MENU) {
    private var inScoresMenu: Boolean = false
    private lateinit var scoresMenu: Pane
    private var mainMenuBtns: VBox

    init {
        mainMenuBtns = VBox(15.0,
            TextButtonKt("Play", this::fireNewGame),
            TextButtonKt("Options") { },
            TextButtonKt("Top Scores", this::toggleHighScores),
            TextButtonKt("Exit", this::fireExit)
        )
        mainMenuBtns.translateX = TILE_LENGTH.toDouble()
        mainMenuBtns.translateY = (9 * TILE_LENGTH + 40).toDouble()

        contentRoot.children.addAll(createBackground(), createLogo(), mainMenuBtns)
    }

    private fun toggleHighScores() {
        if (!inScoresMenu) {
            scoresMenu = TopScoreMenuKt(this::toggleHighScores).topScoreMenu
            switchMenus(mainMenuBtns, scoresMenu)
        } else {
            switchMenus(scoresMenu, mainMenuBtns)
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

    private fun switchMenus(menu1: Pane, menu2: Pane) {
        contentRoot.children.remove(menu1)
        contentRoot.children.add(menu2)
    }
}