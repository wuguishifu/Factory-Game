package com.bramerlabs.factory_game.player;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;

public class PlayerCamera extends Camera {

    private final Player player;
    private static final float minDistance = 2.0f;
    private static final float maxDistance = 20.0f;
    private static final float defaultDistance = 10.0f;

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