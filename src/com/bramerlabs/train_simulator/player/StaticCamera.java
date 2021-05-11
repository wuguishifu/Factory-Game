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

    }

    public Vector3f getSelectionRay() {
        // convert from screen coordinates to normalized device coordinates
        float mouseX = (float) input.getMouseX();
        float mouseY = (float) input.getMouseY();
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetWindowSize(window.getWindowHandle(), w, h);
        int width = w.get(0);
        int height = h.get(0);
        float y = height - mouseY;
        Vector2f normalizeDeviceCoords = new Vector2f(2 * (mouseX / width) - 1, 2 * (y / height) - 1);

        // convert to clip coords
        Vector4f clipCoords = new Vector4f(normalizeDeviceCoords.x, normalizeDeviceCoords.y, -1, 1);

        // convert to eye coords
        Matrix4f invProjection = Matrix4f.invert(window.getProjectionMatrix());
        Vector4f eyeCoords = Matrix4f.multiply(invProjection, clipCoords);

        // convert to world coords
        Matrix4f invView = Matrix4f.invert(Matrix4f.view(position, rotation));
        return Vector3f.normalize(Matrix4f.multiply(invView, eyeCoords).xyz());
    }

}