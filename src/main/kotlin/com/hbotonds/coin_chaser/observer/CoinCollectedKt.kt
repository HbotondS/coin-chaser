package com.hbotonds.coin_chaser.observer

class CoinCollectedKt() {
    private val observers: MutableList<CoinObserverKt>

    init {
        observers = ArrayList()
    }

    fun addObserver(observer: CoinObserverKt) {
        observers.add(observer)
    }

    fun removeObserver(observer: CoinObserverKt) {
        observers.remove(observer)
    }

    fun notifyObservers() {
        observers.forEach(CoinObserverKt::update)
    }
}