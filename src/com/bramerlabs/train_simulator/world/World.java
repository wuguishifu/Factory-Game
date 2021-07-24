package com.bramerlabs.train_simulator.world;

import com.bramerlabs.engine.io.window.Input;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.chunk.Chunk;
import com.bramerlabs.train_simulator.world.chunk.Noise;

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

    public Chunk loadChunk(int x, int y) {
        return Chunk.generateChunk(x, y, noise);
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

    public Vector2f update(Player player, boolean[] keysDown, boolean[] keysDownLast,
                       boolean[] buttonsDown, boolean[] buttonsDownLast, Input input) {
        // show, hide, load, and unload chunks
        updateShownChunks(player);

        // update tiles
        return updateTiles(player, keysDown, keysDownLast, buttonsDown, buttonsDownLast, input);
    }

    public Vector2f updateTiles(Player player, boolean[] keysDown, boolean[] keysDownLast,
                            boolean[] buttonsDown, boolean[] buttonsDownLast, Input input) {
        float tileX = (float) Math.floor(player.getPosition().x/Chunk.TILE_SIZE)*Chunk.TILE_SIZE;
        float tileY = (float) Math.floor(player.getPosition().y/Chunk.TILE_SIZE)*Chunk.TILE_SIZE;
        Vector2f tilePosition = new Vector2f(tileX, tileY);
        float rotation = player.getRotation();
        float delta = Chunk.TILE_SIZE;
        if (rotation < 45) {
            tilePosition = Vector2f.add(tilePosition,  0,  delta);
        } else if (rotation < 135) {
            tilePosition = Vector2f.add(tilePosition,  delta,  0);
        } else if (rotation < 225) {
            tilePosition = Vector2f.add(tilePosition,  0, -delta);
        } else if (rotation < 315) {
            tilePosition = Vector2f.add(tilePosition, -delta,  0);
        } else {
            tilePosition = Vector2f.add(tilePosition,  0,  delta);
        }
        return tilePosition;
    }

    public void updateShownChunks(Player player) {
        Chunk.Key inChunk = new Chunk.Key((int) (player.getPosition().x / (Chunk.SIZE * Chunk.TILE_SIZE)),
                (int) (player.getPosition().y / (Chunk.SIZE * Chunk.TILE_SIZE)));
        visibleChunks.clear();
        for (int x = -3; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
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