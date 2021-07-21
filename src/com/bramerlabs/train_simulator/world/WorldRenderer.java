package com.bramerlabs.train_simulator.world;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.graphics.Shader;
import com.bramerlabs.engine.graphics.renderers.Renderer;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.objects.RenderObject;
import com.bramerlabs.train_simulator.world.chunk.Chunk;
import com.bramerlabs.train_simulator.world.title.Tile;

public class WorldRenderer extends Renderer {

    public WorldRenderer(Window window) {
        super(window);
    }

    public void renderWorld(World world, Camera camera, Shader shader) {
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), camera.getDistance()), new Vector3f(0));
        for (Chunk chunk : world.visibleChunks) {
            Vector2f chunkPosition = Vector2f.scale(chunk.getPosition(), (Chunk.TILE_SIZE * Chunk.SIZE));
            Tile[][] tiles = chunk.getTiles();
            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < Chunk.SIZE; y++) {
                    Tile tile = tiles[x][y];
                    if (tile.getType() != 0) {
                        RenderObject object = Tile.tileSet.get(tile.getType());
                        Vector2f tilePosition = Vector2f.add(chunkPosition,
                                Vector2f.scale(new Vector2f(x, y), Chunk.TILE_SIZE));
                        Vector3f position = new Vector3f(tilePosition, 0.0f);
                        Vector3f rotation = new Vector3f(0, 0, 0);
                        Vector3f scale = new Vector3f(Chunk.TILE_SIZE, Chunk.TILE_SIZE, 0);
                        Matrix4f model = Matrix4f.transform(position, rotation, scale);
                        renderMesh(object, model, view, window.getProjectionMatrix(), shader);
                    }
                }
            }
        }
    }
}