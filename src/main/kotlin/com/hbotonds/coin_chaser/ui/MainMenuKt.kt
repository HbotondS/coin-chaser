package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.app.scene.FXGLMenu
import com.almasb.fxgl.app.scene.MenuType
import com.almasb.fxgl.dsl.getAppHeight
import com.almasb.fxgl.dsl.getAppWidth
import com.almasb.fxgl.dsl.getUIFactoryService
import com.almasb.fxgl.dsl.texture
import com.hbotonds.coin_chaser.MainKt.Companion.TILE_LENGTH
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color

class MainMenuKt() : FXGLMenu(MenuType.MAIN_MENU) {

    init {
        val btnStart = Button("Play", this::fireNewGame)
        val btnOptions = Button("Options") { }
        val btnExit = Button("Exit", this::fireExit)

        val box = VBox(15.0,
                btnStart,
                btnOptions,
                btnExit
        )
        box.translateX = TILE_LENGTH.toDouble()
        box.translateY = (10 * TILE_LENGTH - 10).toDouble()

        contentRoot.children.addAll(createBackground(), box)
    }

    private fun createBackground(): Node {
        val bg = texture("background/Background.png").image
        val iv = ImageView()
        iv.image = bg
        iv.fitHeight = getAppHeight().toDouble()
        iv.fitWidth = getAppWidth().toDouble()
        return iv
    }

    private class Button(name: String, action: Runnable) : StackPane() {
        init {
            val text = getUIFactoryService().newText(name, Color.WHITE, 50.0)
            text.fillProperty().bind(
                    Bindings.`when`(hoverProperty())
                            .then(Color.WHITE)
                            .otherwise(Color.GRAY)
            )
            text.onMouseClicked = EventHandler { action.run() }
            alignment = Pos.CENTER_LEFT
            children.add(text)
        }
    }
}