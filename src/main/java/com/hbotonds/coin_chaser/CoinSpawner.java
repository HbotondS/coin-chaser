package com.hbotonds.coin_chaser;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class CoinSpawner {
    private static final List<Point2D> positions = new ArrayList<>(){{
        add(new Point2D(256, 1536));
        add(new Point2D(1165, 780));
        add(new Point2D(1280, 1410));
        add(new Point2D(1920, 1030));
        add(new Point2D(2692, 1286));
    }};

    public static Entity spawnNewCoin() {
        var position = positions.get((int) (Math.random() * positions.size()));
        return getGameWorld().spawn("coin", position);
    }
}
