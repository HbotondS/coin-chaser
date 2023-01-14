package com.hbotonds.coin_chaser;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGL.spawn;

public class Main extends GameApplication {

    private Entity player;
    private final Logger logger = Logger.get(Main.class);

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(30 * 128);
        settings.setHeight(15 * 128);

        settings.setTitle("Coin Chaser");
        settings.setVersion("1.0-SNAPSHOT");
        settings.setMainMenuEnabled(true);
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
                .when(() -> player.getPosition().getY() > 15 * 128)
                .thenRun(() -> getDialogService().showMessageBox("Game over.", () -> getGameController().exit()))
                .buildAndStart();
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 1000);

        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
            inc("score", +1);
            if (geti("coins2NextLvl") > 0) {
                inc("coins2NextLvl", -1);
            }
            while (true) {
                Entity newCoin = CoinSpawner.spawnNewCoin();
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
        Text scoreTxt = getUIFactoryService().newText("", Color.BLACK, 30);
        scoreTxt.textProperty().bind(getip("score").asString());
        scoreTxt.setTranslateX(30 * 128 - 30);
        scoreTxt.setTranslateY(30);
        scoreTxt.setStroke(Color.BLACK);

        Text coins2NextLvlTxt = getUIFactoryService().newText("", Color.BLACK, 30);
        coins2NextLvlTxt
                .textProperty()
                .bind(getip("coins2NextLvl").asString("Coins required for the next level: %d"));
        coins2NextLvlTxt.setTranslateX(26 * 128);
        coins2NextLvlTxt.setTranslateY(15 * 128 - 20);
        coins2NextLvlTxt.setStroke(Color.BLACK);

        getGameScene().addUINodes(scoreTxt, coins2NextLvlTxt);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
