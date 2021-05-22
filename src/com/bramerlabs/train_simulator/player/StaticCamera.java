package com.bramerlabs.train_simulator.player;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.BufferUtils;

import java.nio.IntBuffer;

public class StaticCamera extends Camera {

    private Window window;

    /**
     * default constructor for specified position, rotation, and input object
     *
     * @param position - the position of the camera object
     * @param rotation - the rotation of the camera object
     * @param input    - the callback input object
     */
    public StaticCamera(Vector3f position, Vector3f rotation, Input input, Window window) {
        super(position, rotation, input);
        this.window = window;
    }

    @Override
    public void setIdealPosition() {
        distance = 3.0f;
        verticalAngle = -60.0f;
        horizontalAngle = 0.0f;

        // get the vertical and horizontal distances
        this.horizontalDistance = (float) (distance * Math.cos(Math.toRadians(verticalAngle))); // using formula h = r*cos(theta_x)
        this.verticalDistance = (float) (distance * Math.sin(Math.toRadians(verticalAngle))); // using formula v = r*sin(theta_x)

        // set the offsets
        float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
        float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

        // set the new camera position based on the object
        this.position.set(focus.getX() + xOffset,
                focus.getY() - verticalDistance,
                focus.getZ() + zOffset);

        // set the new camera rotation based on the object
        this.rotation.set(verticalAngle, -horizontalAngle, 0);
    }

    @Override
    public void update() {
        Vector3f pickingVector = getRayPickingVector();
    }

    public Vector3f getRayPickingVector() {
        return Vector3f.subtract(position, getLookingAt());
    }

}