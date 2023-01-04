package com.HbotondS.coin_chaser;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class Main extends GameApplication {
    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(200);
        settings.setHeight(200);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
