package com.bramerlabs.train_simulator.world;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.chunk.Chunk;
import com.bramerlabs.train_simulator.world.title.Tile;

public class WorldRenderer extends Renderer {

    public WorldRenderer(Window window) {
        super(window);
    }

    public void renderWorld(World world, Player player, Camera camera, Shader shader) {
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), camera.getDistance()), new Vector3f(0));
        for (Chunk chunk : world.visibleChunks) {
            Tile[][] tiles = chunk.getTiles();
            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < Chunk.SIZE; y++) {
                    Tile tile = tiles[x][y];

                }
            }
        }
    }

}
