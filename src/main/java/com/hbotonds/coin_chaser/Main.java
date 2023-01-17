package com.hbotonds.coin_chaser;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.core.util.InputPredicates;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.logging.Logger;
import com.hbotonds.coin_chaser.mongodb.DbController;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScore;
import com.hbotonds.coin_chaser.mongodb.gateway.HighScoreGateway;
import com.hbotonds.coin_chaser.observer.CoinCollected;
import com.hbotonds.coin_chaser.observer.NextLevel;
import com.hbotonds.coin_chaser.observer.Score;
import com.hbotonds.coin_chaser.ui.MainMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.bson.types.ObjectId;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.eventBuilder;
import static com.almasb.fxgl.dsl.FXGL.getDialogService;
import static com.almasb.fxgl.dsl.FXGL.getGameController;
import static com.almasb.fxgl.dsl.FXGL.getGameScene;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.getip;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class Main extends GameApplication {

    private Entity player;
    private CoinCollected coinCollected;
    private static DbController controller;

    @Getter
    private static HighScoreGateway gateway;
    private final Logger logger = Logger.get(Main.class);

    public static final int TILE_LENGTH = 128;
    private final int APP_HEIGHT = 15 * TILE_LENGTH;
    private final int APP_WIDTH = 30 * TILE_LENGTH;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(APP_WIDTH);
        settings.setHeight(APP_HEIGHT);

        settings.setTitle("Coin Chaser");
        settings.setVersion("1.0-SNAPSHOT");
        settings.setMainMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {
                return new MainMenu();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("coins2NextLvl", 3);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new CoinChaseFactory());
        setLevelFromMap("tmx/map.tmx");
        spawn("background");

        player = getGameWorld().spawn("player", 100, 1536);
        CoinSpawner.spawnNewCoin();

        eventBuilder()
                .when(() -> player.getPosition().getY() > getGameScene().getAppHeight())
                .thenRun(() -> getDialogService().showMessageBox("Game over.", () -> {
                    getDialogService().showInputBox("Please enter your name.",
                            InputPredicates.ALPHANUM,
                            (name) -> {
                                if (name.isEmpty()) {
                                    return;
                                }
                                var highScore = new HighScore(
                                        ObjectId.get(),
                                        name,
                                        geti("score")
                                );

                                gateway.insertOne(highScore);
                                getGameController().exit();
                            });
                }))
                .buildAndStart();

        coinCollected = new CoinCollected();
        coinCollected.addObserver(new NextLevel());
        coinCollected.addObserver(new Score());
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 1000);

        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
            coinCollected.notifyObservers();
            while (true) {
                var newCoin = CoinSpawner.spawnNewCoin();
                if (coin.getPosition().equals(newCoin.getPosition())) {
                    newCoin.removeFromWorld();
                } else {
                    break;
                }
            }
            logger.info("collision with coin");
        });
    }

    @Override
    protected void initInput() {
        getInput().addAction(new MyInputAction.Builder("left")
                .setOnAction(() -> player.getComponent(PlayerComponent.class).left())
                .setOnActionEnd(() -> player.getComponent(PlayerComponent.class).stop())
                .build(), KeyCode.A, VirtualButton.LEFT);

        getInput().addAction(new MyInputAction.Builder("right")
                .setOnAction(() -> player.getComponent(PlayerComponent.class).right())
                .setOnActionEnd(() -> player.getComponent(PlayerComponent.class).stop())
                .build(), KeyCode.D, VirtualButton.RIGHT);

        getInput().addAction(new MyInputAction.Builder("jump")
                .setOnAction(() -> player.getComponent(PlayerComponent.class).jump())
                .build(), KeyCode.W, VirtualButton.UP);
    }

    @Override
    protected void initUI() {
        var scoreTxt = getUIFactoryService().newText("", Color.BLACK, 30);
        scoreTxt.textProperty().bind(getip("score").asString());
        scoreTxt.setTranslateX(APP_WIDTH - 30);
        scoreTxt.setTranslateY(30);
        scoreTxt.setStroke(Color.BLACK);

        var coins2NextLvlTxt = getUIFactoryService().newText("", Color.BLACK, 30);
        coins2NextLvlTxt
                .textProperty()
                .bind(getip("coins2NextLvl").asString("Coins required for the next level: %d"));
        coins2NextLvlTxt.setTranslateX(APP_WIDTH - 4 * TILE_LENGTH);
        coins2NextLvlTxt.setTranslateY(APP_HEIGHT - 20);
        coins2NextLvlTxt.setStroke(Color.BLACK);

        getGameScene().addUINodes(scoreTxt, coins2NextLvlTxt);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            controller = new DbController();
            gateway = new HighScoreGateway(controller.getCollection());
        }).start();
        launch(args);
    }
}
