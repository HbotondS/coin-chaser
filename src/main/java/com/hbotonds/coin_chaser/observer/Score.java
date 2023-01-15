package com.hbotonds.coin_chaser.observer;

import static com.almasb.fxgl.dsl.FXGL.inc;

public class Score implements CoinObserver {
    @Override
    public void update() {
        inc("score", +1);
    }
}
