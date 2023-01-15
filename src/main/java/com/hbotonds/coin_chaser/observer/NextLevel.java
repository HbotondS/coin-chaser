package com.hbotonds.coin_chaser.observer;

import static com.almasb.fxgl.dsl.FXGL.geti;
import static com.almasb.fxgl.dsl.FXGL.inc;

public class NextLevel implements CoinObserver {
    @Override
    public void update() {
        if (geti("coins2NextLvl") > 0) {
            inc("coins2NextLvl", -1);
        }
    }
}
