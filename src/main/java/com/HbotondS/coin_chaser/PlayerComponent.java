package com.HbotondS.coin_chaser;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private boolean jumps = false;

    @Override
    public void onAdded() {
        physics.onGroundProperty().addListener((obs, old, isOnGround) -> {
            if (isOnGround) {
                jumps = false;
            }
        });
    }

    public void left() {
        physics.setVelocityX(-400);
    }

    public void right() {
        physics.setVelocityX(400);
    }

    public void stop() {
        physics.setVelocityX(0);
    }

    public void jump() {
        if (jumps) {
            return;
        }

        physics.setVelocityY(-1000);

        jumps = true;
    }
}
