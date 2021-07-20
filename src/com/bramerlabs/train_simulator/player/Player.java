package com.bramerlabs.train_simulator.player;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.glfw.GLFW;

public class Player extends RenderObject {

    private final Input input;

    private static final float maxV = 0.1f;

    public Player(Input input) {
        super(createMesh(), new Vector2f(0, 0), 0, new Vector2f(0.5f, 0.5f));
        this.input = input;
    }

    private static Mesh createMesh() {
        return new Mesh(
                new Vertex[]{
                        new Vertex(new Vector2f(-0.5f, -0.5f), new Vector2f(0, 1)),
                        new Vertex(new Vector2f( 0.5f, -0.5f), new Vector2f(1, 1)),
                        new Vertex(new Vector2f( 0.5f,  0.5f), new Vector2f(1, 0)),
                        new Vertex(new Vector2f(-0.5f,  0.5f), new Vector2f(0, 0)),
                },
                new int[]{0, 1, 2, 0, 2, 3},
                new Material("textures/player.png")
        );
    }

    public void update(boolean[] keysDown, boolean[] keysDownLast) {

        float x = 0, y = 0;
        // handle acceleration
        if (keysDown[GLFW.GLFW_KEY_W]) {
            y += 1;
        }
        if (keysDown[GLFW.GLFW_KEY_S]) {
            y += -1;
        }
        if (keysDown[GLFW.GLFW_KEY_A]) {
            x += -1;
        }
        if (keysDown[GLFW.GLFW_KEY_D]) {
            x += 1;
        }

        if (x != 0 || y != 0) {
            Vector2f velocity = Vector2f.normalize(new Vector2f(x, y), maxV);
            this.position = Vector2f.add(position, velocity);
            if (Vector3f.cross(new Vector3f(velocity, 0.0f), new Vector3f(0, 1, 0)).z < 0) {
                this.rotation = 360 - (float) Math.toDegrees(Math.acos(
                        Vector2f.dot(Vector2f.normalize(velocity), new Vector2f(0, 1))));
            } else {
                this.rotation = (float) Math.toDegrees(Math.acos(
                        Vector2f.dot(Vector2f.normalize(velocity), new Vector2f(0, 1))));
            }
        }
    }
}