package com.hbotonds.coin_chaser.ui;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;

public class TextButton extends StackPane {
    public TextButton(String name, Runnable action) {

        var text = getUIFactoryService().newText(name, Color.WHITE, 50);
        text.fillProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Color.WHITE)
                        .otherwise(Color.GRAY)
        );

        text.setOnMouseClicked(e -> action.run());

        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(text);
    }
}
