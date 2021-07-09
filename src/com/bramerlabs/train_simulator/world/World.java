package com.bramerlabs.train_simulator.world;

import java.awt.*;
import java.util.ArrayList;

public class World {

    ArrayList<Chunk> loadedChunks;
    ArrayList<Chunk> renderChunks;

    public World() {
        loadedChunks = new ArrayList<>();
        renderChunks = new ArrayList<>();
    }

    public void paint(Graphics g) {
        for (Chunk chunk : renderChunks) {
            chunk.paint(g, 0, 0);
        }
    }

    public void generateChunk(int x, int y) {

    }

    public void addChunk(Chunk chunk) {
        this.loadedChunks.add(chunk);
    }

    public void showChunk(Chunk chunk) {
        this.renderChunks.add(chunk);
    }

    public void hideChunk(Chunk chunk) {
        renderChunks.remove(chunk);
    }

}
