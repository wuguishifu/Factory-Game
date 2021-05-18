package com.bramerlabs.train_simulator.main;

import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.train_simulator.player.StaticCamera;
import com.bramerlabs.train_simulator.world.grid.TileGrid;
import com.bramerlabs.train_simulator.world.tile.Tile;
import org.lwjgl.opengl.GL46;

public class TrainSimulator implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private StaticCamera camera;
    private Shader shader;
    private Shader lightShader;
    private Renderer renderer;
    private Vector3f lightPosition = new Vector3f(0, 2.0f, 3.0f);

    private TileGrid tileGrid;

    public static void main(String[] args) {
        new TrainSimulator().start();
    }

    public void start() {
        Thread main = new Thread(this, "Train Simulator");
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

    private void init() {
        window.create();
        shader = new Shader(
                "shaders/default/vertex.glsl",
                "shaders/default/fragment.glsl"
        ).create();
        lightShader = new Shader(
                "shaders/light/vertex.glsl",
                "shaders/default/fragment.glsl"
        ).create();
        renderer = new Renderer(window, lightPosition);
        camera = new StaticCamera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), input, window);
        camera.setFocus(new Vector3f(0, 0, 0));

        tileGrid = new TileGrid();
        tileGrid.loadTile(new Tile(new Vector2f(0, 0)));
        tileGrid.loadTile(new Tile(new Vector2f(0, -1)));
        tileGrid.loadTile(new Tile(new Vector2f(1, 1)));
        tileGrid.loadTile(new Tile(new Vector2f(1, 0)));
        tileGrid.loadTile(new Tile(new Vector2f(0, 1)));
        tileGrid.loadTile(new Tile(new Vector2f(-1, -1)));
        tileGrid.loadTile(new Tile(new Vector2f(-1, 0)));

        camera.setIdealPosition();

    }

    private void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);

        camera.update();
    }

    private void render() {
        for (Tile tile : tileGrid.getLoadedTiles()) {
            renderer.renderMesh(tile.getHexagon(), camera, shader, Renderer.COLOR);
            if (tile.isSelected()) {
                renderer.renderMesh(tile.getSelectionHexagon(), camera, shader, Renderer.COLOR);
            }
        }
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        shader.destroy();
    }

}
