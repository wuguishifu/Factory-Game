package com.bramerlabs.train_simulator.world.chunk;

import com.bramerlabs.train_simulator.world.title.Tile;

public class Chunk {

    private final Tile[][] tiles;

    public static final int SIZE = 16;

    public Chunk() {
        this.tiles = new Tile[SIZE][SIZE];
    }

    public Chunk(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= SIZE || x < 0 || y >= SIZE || y < 0) {
            return;
        }
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        if (x >= SIZE || x < 0 || y >= SIZE || y < 0) {
            return null;
        }
        return tiles[x][y];
    }

    public static Chunk generateChunk(int chunkX, int chunkY, int seed) {
        return null;
    }

}
