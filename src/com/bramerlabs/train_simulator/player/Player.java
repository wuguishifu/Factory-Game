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
    private Vector2f facing;

    public Player(Input input) {
        super(createMesh(), new Vector2f(0, 0), 0, new Vector2f(0.5f, 0.5f));
        this.input = input;
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
                new Material("textures/player.png")
        );
    }

    public void update() {
        float dx = 0, dy = 0;
        if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
            dy += 0.1f;
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_S)) {
            dy += -0.1f;
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_A)) {
            dx += -0.1f;
        }
        if (input.isKeyDown(GLFW.GLFW_KEY_D)) {
            dx += 0.1f;
        }
        Vector2f velocity = Vector2f.normalize(new Vector2f(dx, dy), 0.1f);
        if (dx != 0 || dy != 0) {
            this.facing = Vector2f.normalize(new Vector2f(dx, dy));
        } else {
            velocity = new Vector2f(0, 0);
        }
        this.position = Vector2f.add(this.position, velocity);
        if (Vector3f.cross(new Vector3f(facing, 0.0f), new Vector3f(0, 1, 0)).z < 0) {
            this.rotation = (float) -Math.toDegrees(Math.acos(Vector2f.dot(facing, new Vector2f(0, 1))));
        } else {
            this.rotation = (float) Math.toDegrees(Math.acos(Vector2f.dot(facing, new Vector2f(0, 1))));
        }
    }
}