package com.bramerlabs.train_simulator.world;

import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.chunk.Chunk;

import java.util.ArrayList;

public class World {

    public ArrayList<Chunk> loadedChunks;
    public ArrayList<Chunk> visibleChunks;

    private final int seed;

    public World(int seed) {
        this.seed = seed;
        loadedChunks = new ArrayList<>();
        visibleChunks = new ArrayList<>();
    }

    public Chunk loadChunk(int x, int y) {
        return Chunk.generateChunk(x, y, seed);
    }

    public void setVisible(Chunk chunk) {
        this.visibleChunks.add(chunk);
    }

    public void setInvisible(Chunk chunk) {
        this.visibleChunks.remove(chunk);
    }

    public void unloadChunk(Chunk chunk) {
        this.loadedChunks.remove(chunk);
    }

    public void update(Player player) {
        Vector2f playerPosition = player.getPosition();
        ArrayList<Chunk> chunksOutOfRange = new ArrayList<>();
        for (Chunk chunk : visibleChunks) {
            Vector2f chunkPosition = chunk.getPosition();
            if (Vector2f.distance(chunkPosition, playerPosition) < 10) {
                chunksOutOfRange.add(chunk);
            }
        }
        for (Chunk chunk : chunksOutOfRange) {
            visibleChunks.remove(chunk);
        }
    }

    public int getSeed() {
        return this.seed;
    }
}