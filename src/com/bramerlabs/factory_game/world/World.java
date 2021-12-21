package com.bramerlabs.factory_game.world;

import com.bramerlabs.engine.graphics.Camera;
import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.io.window.Window;
import com.bramerlabs.engine.math.matrix.Matrix4f;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.engine.math.vector.Vector4f;
import com.bramerlabs.factory_game.FactoryGame;
import com.bramerlabs.factory_game.player.Player;
import com.bramerlabs.factory_game.world.chunk.Chunk;
import com.bramerlabs.factory_game.world.chunk.Noise;
import com.bramerlabs.factory_game.world.title.Tile;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;

public class World {

    public HashMap<Chunk.Key, Chunk> loadedChunks;
    public HashMap<Chunk.Key, Chunk> visibleChunks;

    private final int seed;
    private final Noise noise;

    public World(int seed) {
        this.seed = seed;
        this.noise = new Noise(seed);
        loadedChunks = new HashMap<>();
        visibleChunks = new HashMap<>();
    }

    public Tile getTileInWorld(Vector2f tileLocationInWorld) {
        int tileX, tileY;
        if (tileLocationInWorld.x < 0) {
            tileLocationInWorld.x -= Chunk.SIZE - 1;
            tileX = (int) (tileLocationInWorld.x % Chunk.SIZE);
            tileX += Chunk.SIZE - 1;
        } else {
            tileX = (int) (tileLocationInWorld.x % Chunk.SIZE);
        }
        if (tileLocationInWorld.y < 0) {
            tileLocationInWorld.y -= Chunk.SIZE - 1;
            tileY = (int) (tileLocationInWorld.y % Chunk.SIZE);
            tileY += Chunk.SIZE - 1;
        } else {
            tileY = (int) (tileLocationInWorld.y % Chunk.SIZE);
        }
        int chunkX = (int) (tileLocationInWorld.x / Chunk.SIZE);
        int chunkY = (int) (tileLocationInWorld.y / Chunk.SIZE);
        return visibleChunks.get(new Chunk.Key(chunkX, chunkY)).getLandTile(tileX, tileY);
    }

    public void setVisible(Chunk.Key chunkPos) {
        if (!loadedChunks.containsKey(chunkPos)) {
            loadedChunks.put(chunkPos, Chunk.generateChunk(chunkPos.x, chunkPos.y, noise));
        }
        visibleChunks.put(chunkPos, loadedChunks.get(chunkPos));
    }

    public void setInvisible(Chunk.Key chunkPos) {
        this.visibleChunks.remove(chunkPos);
    }

    public void unloadChunk(Chunk.Key chunkPos) {
        this.loadedChunks.remove(chunkPos);
    }

    public int getSeed() {
        return this.seed;
    }

    public void update(Player player, boolean[] keysDown, boolean[] keysDownLast, boolean[] buttonsDown,
                           boolean[] buttonsDownLast, Input input, Window window, Camera camera) {
        // show, hide, load, and unload chunks
        updateShownChunks(player);
    }

    public Vector2f selectTilePosition(Player player, Input input, Window window, Camera camera) {
        Vector2f normalizedCoords = new Vector2f(((2f * input.getMouseX()) / (input.getWindowWidth()) - 1f),
                ((2f * input.getMouseY()) / (input.getWindowHeight()) - 1f));
        Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
        Matrix4f inverseProjection = Matrix4f.invert(window.getProjectionMatrix());
        Vector4f eyeCoords = Matrix4f.multiply(inverseProjection, clipCoords);
        Matrix4f view = Matrix4f.view(new Vector3f(camera.getPosition(), camera.getDistance()), new Vector3f(0));
        Matrix4f inverseView = Matrix4f.invert(view);
        Vector4f worldCoords = Matrix4f.multiply(inverseView, eyeCoords);
        return worldCoords.xyz().xy().multiply(camera.getDistance(), -camera.getDistance()).add(player.getPosition());
    }

    public void updateShownChunks(Player player) {
        Chunk.Key inChunk = new Chunk.Key((int) (player.getPosition().x / (Chunk.SIZE * Chunk.TILE_SIZE)),
                (int) (player.getPosition().y / (Chunk.SIZE * Chunk.TILE_SIZE)));
        visibleChunks.clear();
        for (int x = -3; x <= 2; x++) {
            for (int y = -3; y <= 2; y++) {
                setVisible(new Chunk.Key(inChunk.x + x, inChunk.y + y));
            }
        }

        ArrayList<Chunk.Key> keysToUnload = new ArrayList<>();
        for (Chunk.Key key : loadedChunks.keySet()) {
            if (Math.abs(key.x - inChunk.x) > 5 || Math.abs(key.y - inChunk.y) > 5) {
                keysToUnload.add(key);
            }
        }
        for (Chunk.Key key : keysToUnload) {
            unloadChunk(key);
        }
    }
}