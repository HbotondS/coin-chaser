package com.hbotonds.coin_chaser.observer;

import java.util.ArrayList;
import java.util.List;

public class CoinCollected {
    private final List<CoinObserver> observers;

    public CoinCollected() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(CoinObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CoinObserver observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(CoinObserver::update);
    }
}
