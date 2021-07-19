package com.bramerlabs.train_simulator;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.objects.shapes_2d.Square;
import com.bramerlabs.train_simulator.player.Player;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

public class Main implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Square square;
    private Player player;

    private boolean[] keysDown;
    private boolean[] keysDownLast;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Thread main = new Thread(this, "Test Thread");
        main.start();
    }

    public void run() {
        this.init();
        while (!window.shouldClose()) {
            this.update();
            this.render();
        }
        this.close();
    }

    public void init() {
        window.create();
        shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
        renderer = new Renderer(window);
        camera = new Camera(new Vector2f(0, 0), input);
        square = new Square(
                new Material("textures/test.png"),
                new Vector2f(0, 0),
                0,
                new Vector2f(1, 1));
        player = new Player(input);

        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownLast = new boolean[GLFW.GLFW_KEY_LAST];
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);

        // update objects
        player.update(keysDown, keysDownLast);

        camera.update();

        // update keys
        System.arraycopy(keysDown, 0, keysDownLast, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);
    }

    public void render() {
        float distance = 5.0f;
        renderer.renderMesh(square, camera, shader, distance);
        renderer.renderMesh(player, camera, shader, distance);
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        square.destroy();
        shader.destroy();
    }

}
