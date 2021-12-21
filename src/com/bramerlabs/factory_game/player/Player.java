package com.bramerlabs.factory_game.player;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Mesh;
import com.bramerlabs.engine.graphics.Vertex;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import org.lwjgl.glfw.GLFW;

public class Player extends RenderObject {

    private static final float maxV = 0.2f;
    private static final float accM = 0.015f;
    private static final float frictionMagnitudeMax = 0.1f;

    private Vector2f velocity;
    private Vector2f facing;

    public Player() {
        super(createMesh(), new Vector2f(0, 0), 0, new Vector2f(1f, 1f));
        velocity = new Vector2f(0, 0);
        facing = new Vector2f(0, 1);
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
                new Material("textures/assets/player.png")
        );
    }

    public void update(boolean[] keysDown, boolean[] keysDownLast) {

        float x = 0, y = 0;
        // handle acceleration
        if (keysDown[GLFW.GLFW_KEY_W]) {
            y += 1;
        }
        if (keysDown[GLFW.GLFW_KEY_S]) {
            y -= 1;
        }
        if (keysDown[GLFW.GLFW_KEY_A]) {
            x -= 1;
        }
        if (keysDown[GLFW.GLFW_KEY_D]) {
            x += 1;
        }

        if (!this.velocity.equals(new Vector2f(0, 0))) {
            this.facing = Vector2f.normalize(velocity);
        }

        // apply acceleration
        if (x != 0 || y != 0) {
            Vector2f acceleration = Vector2f.normalize(new Vector2f(x, y), accM);
            this.velocity = Vector2f.add(this.velocity, acceleration);
            if (this.velocity.length() > (keysDown[GLFW.GLFW_KEY_LEFT_SHIFT] ? 2.0f * maxV : maxV)) {
                this.velocity = Vector2f.add(this.velocity, Vector2f.scale(this.velocity, -0.1f));
            }
        }

        // apply friction
        if (!velocity.equals(new Vector2f(0, 0))) {
            float frictionMagnitude = Math.max(Math.min(frictionMagnitudeMax, velocity.length() * 0.04f), 0.001f);
            Vector2f friction = Vector2f.normalize(velocity, -frictionMagnitude);
            Vector2f newVelocity = Vector2f.add(velocity, friction);
            if (Vector2f.dot(newVelocity, velocity) < 0) {
                velocity = new Vector2f(0, 0);
            } else {
                velocity = Vector2f.add(velocity, friction);
            }
        }

        // update position
        this.position = Vector2f.add(position, velocity);

        // update the rotation
        if (Vector3f.cross(new Vector3f(facing, 0.0f), new Vector3f(0, 1, 0)).z < 0) {
            this.rotation = 360 - (float) Math.toDegrees(Math.acos(
                    Vector2f.dot(Vector2f.normalize(facing), new Vector2f(0, 1))));
        } else {
            this.rotation = (float) Math.toDegrees(Math.acos(
                    Vector2f.dot(Vector2f.normalize(facing), new Vector2f(0, 1))));
            }
        }
}