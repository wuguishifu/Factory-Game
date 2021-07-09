package com.bramerlabs.train_simulator.world;

import java.awt.*;

public class Chunk {

    private int x, y;

    int[][] grid;

    private final static int chunkSize = 16;
    int cellSize = 20;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;

        grid = new int[16][16];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void paint(Graphics g, int xOffset, int yOffset) {
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                g.drawRect(i * chunkSize + xOffset, j * chunkSize + yOffset, chunkSize, chunkSize);
            }
        }
    }
}
