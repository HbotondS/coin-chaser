package com.hbotonds.coin_chaser.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.hbotonds.coin_chaser.Main.TILE_LENGTH;

public class MainMenu extends FXGLMenu {

    public MainMenu() {
        super(MenuType.MAIN_MENU);
        var btnStart = new Button("Play", this::fireNewGame);
        var btnOptions = new Button("Options", () -> {
        });
        var btnExit = new Button("Exit", this::fireExit);

        var box = new VBox(15,
                btnStart,
                btnOptions,
                btnExit
        );
        box.setTranslateX(TILE_LENGTH);
        box.setTranslateY(10 * TILE_LENGTH - 10);

        getContentRoot().getChildren().addAll(createBackground(), createLogo(), box);
    }

    private Node createBackground() {
        var bg = texture("background/Background.png").getImage();
        var iv = new ImageView();
        iv.setImage(bg);
        iv.setFitHeight(getAppHeight());
        iv.setFitWidth(getAppWidth());
        return iv;
    }

    private Node createLogo() {
        var logo = texture("logo/coin-chaser-logo.png").getImage();
        var iv = new ImageView(logo);
        iv.setTranslateX(11 * TILE_LENGTH);
        iv.setTranslateY(TILE_LENGTH);
        return iv;
    }

    private static class Button extends StackPane {

        public Button(String name, Runnable action) {

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
}
