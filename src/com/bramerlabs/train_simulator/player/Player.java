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

    private Vector2f velocity, friction;
    private static final float acceleration = 0.005f;
    private static final float frictionMagnitude = 0.002f;
    private static final float maxV = 0.1f;

    private float direction, facing;

    public Player(Input input) {
        super(createMesh(), new Vector2f(0, 0), 0, new Vector2f(0.5f, 0.5f));
        this.input = input;
        velocity = new Vector2f(0, 0);
        friction = new Vector2f(0, 0);
        direction = 0;
        facing = 0;
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

        // handle button inputs
        boolean up = keysDown[GLFW.GLFW_KEY_W];
        boolean down = keysDown[GLFW.GLFW_KEY_S];
        boolean left = keysDown[GLFW.GLFW_KEY_A];
        boolean right = keysDown[GLFW.GLFW_KEY_D];

        // handle acceleration
        if (up) {
            velocity = Vector2f.add(velocity, new Vector2f(0, acceleration));
        }
        if (down) {
            velocity = Vector2f.add(velocity, new Vector2f(0, -acceleration));
        }
        if (left) {
            velocity = Vector2f.add(velocity, new Vector2f(-acceleration, 0));
        }
        if (right) {
            velocity = Vector2f.add(velocity, new Vector2f(acceleration, 0));
        }
        if (Vector2f.length(velocity) > maxV) {
            velocity.normalize(maxV);
        }

        // handle friction
        if (velocity.length() > 0) {
            friction = Vector2f.normalize(Vector2f.subtract(new Vector2f(0, 0), velocity), frictionMagnitude);
            velocity = Vector2f.add(velocity, friction);
        }
        if (velocity.length() < acceleration / 2) {
            velocity = new Vector2f(0, 0);
        }

        // update position
        this.position = Vector2f.add(position, velocity);

        if (velocity.length() > 0) {
            // update rotation
            if (Vector3f.cross(new Vector3f(velocity, 0.0f), new Vector3f(0, 1, 0)).z < 0) {
                direction = 360 - (float) Math.toDegrees(Math.acos(
                        Vector2f.dot(Vector2f.normalize(velocity), new Vector2f(0, 1))));
            } else {
                direction = (float) Math.toDegrees(Math.acos(
                        Vector2f.dot(Vector2f.normalize(velocity), new Vector2f(0, 1))));
            }
            this.rotation = direction;
        }
    }
}