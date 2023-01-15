package com.hbotonds.coin_chaser.observer

import com.almasb.fxgl.dsl.FXGL

class NextLevelKt: CoinObserverKt {
    override fun update() {
        if (FXGL.geti("coins2NextLvl") > 0) {
            FXGL.inc("coins2NextLvl", -1)
        }
    }
}