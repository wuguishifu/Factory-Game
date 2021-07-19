package com.bramerlabs.engine.test;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.objects.shapes_2d.Square;
import org.lwjgl.opengl.GL46;

public class TestMain implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private Camera camera;
    private Shader shader;
    private Renderer renderer;
    private Square square;

    public static void main(String[] args) {
        new TestMain().start();
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
                new Material("textures/texture.png"),
                new Vector2f(0, 0),
                new Vector2f(0, 0),
                new Vector2f(1, 1));
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        camera.update();
    }

    public void render() {
        renderer.renderMesh(square, camera, shader);
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        square.destroy();
        shader.destroy();
    }

}
