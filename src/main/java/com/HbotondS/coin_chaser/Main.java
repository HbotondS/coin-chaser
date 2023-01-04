package com.HbotondS.coin_chaser;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(30 * 16);
        settings.setHeight(20 * 16);
    }

    @Override
    protected void initGame() {
        setLevelFromMap("tmx/map.tmx");
        getGameWorld().addEntityFactory(new MapFactory());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
