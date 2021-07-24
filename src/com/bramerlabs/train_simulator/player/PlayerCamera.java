package com.bramerlabs.train_simulator.player;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;

public class PlayerCamera extends Camera {

    private final Player player;
    private static final float minDistance = 1.0f;
//    private static final float maxDistance = 10.0f;
    private static final float maxDistance = 30.0f;
//    private static final float defaultDistance = 5.0f;
    private static final float defaultDistance = 30.0f;

    public PlayerCamera(Player player, Input input) {
        super(player == null ? new Vector2f(0, 0) : player.getPosition(), input);
        this.player = player;
        this.distance = defaultDistance;
    }

    @Override
    public void update() {
        this.moveTo(player.getPosition());
        this.distance = defaultDistance - (float) (input.getScrollY() * 0.5f);
        if (this.distance < minDistance) {
            this.distance = minDistance;
            input.setScrollY(2 * (defaultDistance - minDistance));
        } else if (this.distance > maxDistance) {
            this.distance = maxDistance;
            input.setScrollY(2 * (defaultDistance - maxDistance));
        }
    }
}