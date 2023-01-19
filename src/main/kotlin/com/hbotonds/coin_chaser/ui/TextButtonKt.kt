package com.hbotonds.coin_chaser.ui

import com.almasb.fxgl.dsl.getUIFactoryService
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Pos
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color

class TextButtonKt(name: String, action: Runnable) : StackPane() {
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