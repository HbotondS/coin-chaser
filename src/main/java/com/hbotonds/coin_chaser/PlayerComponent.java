package com.hbotonds.coin_chaser;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private final int speed = 400;

    public void left() {
        physics.setVelocityX(-speed);
    }

    public void right() {
        physics.setVelocityX(speed);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (physics.isOnGround()) {
            physics.setVelocityY(-1000);
        }
    }
}
