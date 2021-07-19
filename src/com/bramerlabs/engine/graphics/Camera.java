package com.bramerlabs.engine.graphics;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;

public class Camera {

    private Vector2f position;
    private Input input;

    public Camera(Vector2f position, Input input) {
        this.position = position;
        this.input = input;
    }

    public void update() {

    }

    public void moveTo(Vector2f position) {
        this.position = position;
    }

    public Vector2f getPosition() {
        return this.position;
    }

}
