package com.hbotonds.coin_chaser.ui;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import static com.almasb.fxgl.dsl.FXGLForKtKt.texture;
import static com.hbotonds.coin_chaser.CoinChaserApp.TILE_LENGTH;
import static com.hbotonds.coin_chaser.CoinChaserApp.main;

public class MainMenu extends FXGLMenu {
    private boolean inScoresMenu = false;
    private Pane scoresMenu;
    private final VBox mainMenuBtns;

    public MainMenu() {
        super(MenuType.MAIN_MENU);

        mainMenuBtns = new VBox(15,
                new TextButton("Play", this::fireNewGame),
                new TextButton("Options", () -> {}),
                new TextButton("Top Scores", this::toggleHighScores),
                new TextButton("Exit", this::fireExit)
        );
        mainMenuBtns.setTranslateX(TILE_LENGTH);
        mainMenuBtns.setTranslateY(9 * TILE_LENGTH + 40);

        getContentRoot().getChildren().addAll(createBackground(), createLogo(), mainMenuBtns);
    }

    private void toggleHighScores() {
        if (!inScoresMenu) {
            scoresMenu = new TopScoreMenu(this::toggleHighScores).getTopScoreMenu();
            switchMenus(mainMenuBtns, scoresMenu);
        } else {
            switchMenus(scoresMenu, mainMenuBtns);
        }
        inScoresMenu = !inScoresMenu;
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

    private void switchMenus(Pane menu1, Pane menu2) {
        getContentRoot().getChildren().remove(menu1);
        getContentRoot().getChildren().add(menu2);
    }
}
