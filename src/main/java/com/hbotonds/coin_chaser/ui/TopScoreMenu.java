package com.hbotonds.coin_chaser.ui;

import com.almasb.fxgl.logging.Logger;
import com.hbotonds.coin_chaser.CoinChaserApp;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import com.mongodb.client.FindIterable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.stream.StreamSupport;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getUIFactoryService;
import static com.hbotonds.coin_chaser.CoinChaserApp.TILE_LENGTH;

public class TopScoreMenu {
    @Getter
    private final Pane topScoreMenu = new Pane();
    private final Runnable toggleHighScores;

    private final Logger logger = Logger.get(TopScoreMenu.class);

    public TopScoreMenu(Runnable toggleHighScores) {
        var namesTxtList = new VBox(15);
        var scoresTxtList = new VBox(15);
        var topScoreList = createTopScoreList();
        if (topScoreList != null) {
            StreamSupport.stream(topScoreList.spliterator(), false)
                    .forEach(highScore -> {
                        namesTxtList.getChildren().add(
                                getUIFactoryService().newText(highScore.getName(), Color.GRAY, 50)
                        );
                        scoresTxtList.getChildren().add(
                                getUIFactoryService().newText(Integer.toString(highScore.getScore()), Color.GRAY, 50)
                        );
                    });
        }
        var highScoreTxtList = new HBox(200,
                namesTxtList,
                scoresTxtList
        );
        highScoreTxtList.setTranslateX(13 * TILE_LENGTH);
        highScoreTxtList.setTranslateY(5 * TILE_LENGTH);


        this.toggleHighScores = toggleHighScores;

        this.topScoreMenu.getChildren().addAll(
                highScoreTxtList,
                createMenuTitle(),
                createBackBtn()
        );
    }

    private Text createMenuTitle() {
        var title = getUIFactoryService().newText("Top Scores", Color.GRAY, 60);
        title.setTranslateX(14 * TILE_LENGTH - 70);
        title.setTranslateY(4 * TILE_LENGTH);

        return title;
    }

    private FindIterable<HighScore> createTopScoreList() {
        try {
            return CoinChaserApp.getGateway().findTopScores();
        } catch (Exception e) {
            logger.fatal("An error occurred while attempting to get top score:", e);
            return null;
        }
    }

    private TextButton createBackBtn() {
        var backBtn = new TextButton("Back", toggleHighScores);
        backBtn.setTranslateX(TILE_LENGTH);
        backBtn.setTranslateY(11 * TILE_LENGTH + 10);

        return backBtn;
    }
}
