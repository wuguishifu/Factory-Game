package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;

public class Camera {

    protected Vector2f position;
    protected Input input;
    protected float distance;

    public Camera(Vector2f position, Input input) {
        this.position = position;
        this.input = input;
        distance = 1.0f;
    }

    public void update() {

    }

    public void moveTo(Vector2f position) {
        this.position = position;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public float getDistance() {
        return this.distance;
    }

}
