package com.hbotonds.coin_chaser.ui.dialog

import com.almasb.fxgl.core.util.EmptyRunnable
import com.almasb.fxgl.dsl.getUIFactoryService
import com.almasb.fxgl.ui.DialogFactoryService
import javafx.beans.binding.Bindings
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import java.util.function.Consumer
import java.util.function.Predicate
import kotlin.math.max

class CustomDialogFactoryServiceKt: DialogFactoryService() {
    override fun messageDialog(message: String, callback: Runnable): Pane {
        val text = getUIFactoryService().newText(message, 40.0)
        val btnOK = getUIFactoryService().newButton("OK")
        btnOK.setOnAction { callback.run() }

        val vbox = VBox(50.0, text, btnOK)
        vbox.alignment = Pos.CENTER

        return wrap(vbox)
    }

    override fun messageDialog(message: String): Pane {
        return messageDialog(message, EmptyRunnable)
    }

    override fun confirmationDialog(message: String, callback: Consumer<Boolean>): Pane {
        val text = getUIFactoryService().newText(message, 40.0)
        val btnYes = getUIFactoryService().newButton("Yes")
        val btnNo = getUIFactoryService().newButton("No")
        btnYes.setOnAction { callback.accept(true) }
        btnNo.setOnAction { callback.accept(false) }

        btnYes.backgroundProperty().bind(
            Bindings.`when`(btnYes.hoverProperty())
                .then(Background(BackgroundFill(Color.GREEN, null, null)))
                .otherwise(Background(BackgroundFill(Color.DIMGRAY, null, null)))
        )
        btnNo.backgroundProperty().bind(
            Bindings.`when`(btnNo.hoverProperty())
                .then(Background(BackgroundFill(Color.RED, null, null)))
                .otherwise(Background(BackgroundFill(Color.DIMGRAY, null, null)))
        )

        val hBox = HBox(10.0, btnYes, btnNo)
        hBox.alignment = Pos.CENTER

        val vBox = VBox(50.0, text, hBox)
        vBox.alignment = Pos.CENTER

        return wrap(vBox)
    }

    override fun <T : Any?> choiceDialog(
        message: String?,
        resultCallback: Consumer<T>?,
        firstOption: T,
        vararg options: T
    ): Pane {
        throw NotImplementedError()
    }

    override fun inputDialog(message: String, callback: Consumer<String>): Pane {
        return inputDialog(message, { true }, callback)
    }

    override fun inputDialog(message: String, filter: Predicate<String>, callback: Consumer<String>): Pane {
        val text = getUIFactoryService().newText(message, 40.0)

        val field = TextField()
        field.maxWidth = max(text.layoutBounds.width, 200.0)
        field.font = getUIFactoryService().newFont(20.0)
        field.focusedProperty().addListener { _, _, isFocused ->
            if (!isFocused && field.scene != null) {
                field.requestFocus()
            }
        }
        field.sceneProperty().addListener { _, _, scene ->
            if (scene != null) {
                field.requestFocus()
            }
        }
        field.setOnAction {
            val newInput = field.text

            if (newInput.isEmpty() || !filter.test(newInput)) {
                return@setOnAction
            }

            callback.accept(newInput)
        }

        val btnOK = getUIFactoryService().newButton("OK")
        btnOK.isDisable = true
        btnOK.setOnAction { callback.accept(field.text) }

        field.textProperty().addListener { _, _, newInput ->
            btnOK.isDisable = newInput.isEmpty() || !filter.test(newInput)
        }

        val vBox = VBox(50.0, text, field, btnOK)
        vBox.alignment = Pos.CENTER

        return wrap(vBox)
    }

    override fun inputDialogWithCancel(
        message: String?,
        filter: Predicate<String>?,
        callback: Consumer<String>?
    ): Pane {
        throw NotImplementedError()
    }

    override fun errorDialog(error: Throwable?, callback: Runnable?): Pane {
        throw NotImplementedError()
    }

    override fun errorDialog(error: Throwable?): Pane {
        throw NotImplementedError()
    }

    override fun errorDialog(errorMessage: String?): Pane {
        throw NotImplementedError()
    }

    override fun errorDialog(errorMessage: String?, callback: Runnable?): Pane {
        throw NotImplementedError()
    }

    override fun progressDialog(message: String?, observable: ReadOnlyDoubleProperty?, callback: Runnable?): Pane {
        throw NotImplementedError()
    }

    override fun progressDialogIndeterminate(message: String?, callback: Runnable?): Pane {
        throw NotImplementedError()
    }

    override fun customDialog(message: String?, content: Node?, callback: Runnable?, vararg buttons: Button?): Pane {
        throw NotImplementedError()
    }

    private fun wrap(n: Node): Pane {
        val wrapper = StackPane(n)
        wrapper.minWidth = 600.0
        wrapper.padding = Insets(20.0)
        wrapper.styleClass.add("dialog-border")

        return wrapper
    }
}
