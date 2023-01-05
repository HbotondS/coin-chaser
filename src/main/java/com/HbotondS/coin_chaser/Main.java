package com.HbotondS.coin_chaser;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.virtual.VirtualButton;
import com.almasb.fxgl.logging.Logger;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.getInput;
import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGL.onCollisionBegin;
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

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
    protected void initGame() {
        getGameWorld().addEntityFactory(new MapFactory());
        setLevelFromMap("tmx/map.tmx");

        player = getGameWorld().spawn("player", 200, 200);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 1000);

        onCollisionBegin(EntityType.PLAYER, EntityType.COIN, (player, coin) -> {
            coin.removeFromWorld();
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

    public static void main(String[] args) {
        launch(args);
    }
}
