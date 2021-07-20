package com.bramerlabs.train_simulator.world;

import com.bramerlabs.train_simulator.world.chunk.Chunk;

import java.util.ArrayList;

public class World {

    public ArrayList<Chunk> loadedChunks;
    public ArrayList<Chunk> visibleChunks;

    public World() {

        loadedChunks = new ArrayList<>();
        visibleChunks = new ArrayList<>();

    }

    public static Chunk loadChunk(int x, int y, int seed) {
        return null;
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

}
