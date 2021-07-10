package com.bramerlabs.train_simulator.world;

import com.bramerlabs.train_simulator.engine.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class World {

    public static Color C1 = new Color(82, 151, 172);
    public static Color C2 = new Color(166, 198, 193);
    public static Color C3 = new Color(230, 224, 207);
    public static Color C4 = new Color(222, 191, 181);
    public static Color C5 = new Color(186, 166, 165);

    HashMap<Key, Chunk> loadedChunks;
    HashMap<Key, Chunk> renderChunks;

    public World() {
        loadedChunks = new HashMap<>();
        renderChunks = new HashMap<>();
    }

    public void paint(Graphics g, float dx, float dy) {
        for (Chunk chunk : renderChunks.values()) {
            chunk.paint(g, dx, dy);
        }
    }

    public void generateChunk(int x, int y) {

    }

    public void addChunk(Chunk chunk) {
        this.loadedChunks.put(new Key(chunk.getX(), chunk.getY()), chunk);
    }

    public void showChunk(Key key) {
        this.renderChunks.put(key, loadedChunks.getOrDefault(key, null));
    }

    public void hideChunk(Key key) {
        renderChunks.remove(key);
    }

    public void updateSize(Dimension windowSize) {
        Chunk.updateSize(windowSize);
    }

    public void updateCell(Vector2f mouse, Vector2f player) {
        Vector2f gridPosition = Vector2f.add(player, mouse);
        gridPosition = new Vector2f((float) Math.floor(gridPosition.x), (float) Math.floor(gridPosition.y));
        Vector2f chunk = new Vector2f((float) Math.floor(gridPosition.x / Chunk.chunkSize),
                (float) Math.floor(gridPosition.y / Chunk.chunkSize));
        Vector2f cell = new Vector2f((float) Math.floor(gridPosition.x % Chunk.chunkSize),
                (float) Math.floor(gridPosition.y % Chunk.chunkSize));
        if (gridPosition.x < 0) {
            cell.x = Chunk.chunkSize - 1 - (float) Math.ceil((-gridPosition.x - 1) % Chunk.chunkSize);
        }
        if (gridPosition.y < 0) {
            cell.y = Chunk.chunkSize - 1 - (float) Math.ceil((-gridPosition.y - 1) % Chunk.chunkSize);
        }

        Chunk updateChunk = loadedChunks.get(new Key((int) chunk.x, (int) chunk.y));
        if (updateChunk != null) {
            updateChunk.updateCell((int) cell.x, (int) cell.y);
        }
    }

    public static class Key {
        private final int x, y;
        public Key(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return x == key.x && y == key.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

}
