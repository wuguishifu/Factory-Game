package com.bramerlabs.train_simulator.world.chunk;

import com.bramerlabs.engine.math.noise.ImprovedNoise;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.math.vector.Vector3f;
import com.bramerlabs.train_simulator.world.title.Tile;

public class Chunk {

    private final Tile[][] tiles;

    public static final int SIZE = 16;
    public static final float TILE_SIZE = 0.5f;

    private final Vector2f position;

    public Chunk(Vector2f position, Tile[][] tiles) {
        this.tiles = tiles;
        this.position = position;
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

    public Vector2f getPosition() {
        return this.position;
    }

    public static Tile[][] generateTiles(int chunkX, int chunkY, int seed) {
        Tile[][] tiles = new Tile[SIZE][SIZE];
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                float seedSize = 10.f;
                float sampleX = (chunkX * SIZE + x) / seedSize;
                float sampleY = (chunkY * SIZE + y) / seedSize;
                double noise = ImprovedNoise.noise(sampleX, sampleY, seed);
                int type = noise < 0.1f ? -1 : 0;
                System.out.println((new Vector3f(chunkX, chunkY, seed)) + ", " +
                        new Vector2f(chunkX * SIZE + x, chunkY * SIZE + y) + ", " + noise + ", " + type);
                tiles[x][y] = new Tile(type);
            }
        }
        return tiles;
    }

    public static Chunk generateChunk(int chunkX, int chunkY, int seed) {
        Tile[][] tiles = generateTiles(chunkX, chunkY, seed);
        return new Chunk(new Vector2f(chunkX, chunkY), tiles);
    }

}
