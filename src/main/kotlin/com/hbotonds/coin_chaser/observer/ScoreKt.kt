package com.hbotonds.coin_chaser.observer

import com.almasb.fxgl.dsl.FXGL

class ScoreKt: CoinObserverKt {
    override fun update() {
        FXGL.inc("score", +1)
    }
}