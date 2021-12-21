package com.bramerlabs.factory_game;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.objects.shapes_2d.Square;
import com.bramerlabs.factory_game.player.Player;
import com.bramerlabs.factory_game.player.PlayerCamera;
import com.bramerlabs.factory_game.world.World;
import com.bramerlabs.factory_game.world.WorldRenderer;
import com.bramerlabs.factory_game.world.chunk.Chunk;
import com.bramerlabs.factory_game.world.title.Tile;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL46;

public class FactoryGame implements Runnable {

    private final Input input = new Input();
    private final Window window = new Window(input);
    private PlayerCamera camera;
    private Shader shader;
    private WorldRenderer renderer;
    private Player player;

    private boolean[] keysDown, keysDownLast;
    private boolean[] buttonsDown, buttonsDownLast;

    private World world;

    public static void main(String[] args) {
        new FactoryGame().start();
    }

    public void start() {
        Thread main = new Thread(this, "Main Thread");
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
        player = new Player();
        shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
        renderer = new WorldRenderer(window);
        camera = new PlayerCamera(player, input);

        keysDown = new boolean[GLFW.GLFW_KEY_LAST];
        keysDownLast = new boolean[GLFW.GLFW_KEY_LAST];
        buttonsDown = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        buttonsDownLast = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
        Tile.generateTiles();

        input.setWindowWidth(window.getWidth());
        input.setWindowHeight(window.getHeight());

        world = new World(0);
        int size = 2;
        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                world.setVisible(new Chunk.Key(i, j));
            }
        }
    }

    public void update() {
        window.update();
        GL46.glClearColor(window.r, window.g, window.b, 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT);


        // update objects
        player.update(keysDown, keysDownLast);

        // update the world
        world.update(player, keysDown, keysDownLast, buttonsDown, buttonsDownLast, input, window, camera);

        if (buttonClicked(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            Vector2f coord = world.selectTilePosition(player, input, window, camera);
            Vector2f selectedTileLocationInWorld = new Vector2f(coord).add(Chunk.TILE_SIZE/2, Chunk.TILE_SIZE/2).floor(1/Chunk.TILE_SIZE).scale(1/Chunk.TILE_SIZE);
            Tile tile = world.getTileInWorld(selectedTileLocationInWorld);
            tile.setType(4);
        }

        // update the camera
        camera.update();

        // update input source
        System.arraycopy(keysDown, 0, keysDownLast, 0, keysDown.length);
        System.arraycopy(input.getKeysDown(), 0, keysDown, 0, input.getKeysDown().length);
        System.arraycopy(buttonsDown, 0, buttonsDownLast, 0, buttonsDown.length);
        System.arraycopy(input.getButtonsDown(), 0, buttonsDown, 0, input.getButtonsDown().length);
    }

    public void render() {
        renderer.renderWorldNaive(world, camera, shader);
        renderer.renderMesh(player, camera, shader);
        window.swapBuffers();
    }

    public void close() {
        window.destroy();
        shader.destroy();
        renderer.destroy();
        player.destroy();
        Tile.destroyTextures();
    }

    public boolean buttonClicked(int buttonCode) {
        return buttonsDown[buttonCode] && !buttonsDownLast[buttonCode];
    }

    public boolean keyClicked(int keyCode) {
        return keysDown[keyCode] && !keysDownLast[keyCode];
    }

}
