package com.bramerlabs.train_simulator.world;

import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;

import java.awt.*;

public class Chunk {

    private int x, y;

    int[][] grid;

    public final static int chunkSize = 16;
    public final static int defaultCellSize = Main.defaultWindowSize.width / 20;
    public static float cellSize = defaultCellSize;

    private static final int defaultZoom = 20;
    private static float zoom = defaultZoom;
    private static final float maxZoom = 40, minZoom = 4;

    private static Dimension windowSize = Main.defaultWindowSize;
    private static Point winCenter = new Point(Main.defaultWindowSize.width/2, Main.defaultWindowSize.height/2);

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

    public void paint(Graphics g, float xOffset, float yOffset) {
        int x0 = (int) (((x * chunkSize) - xOffset) * cellSize) + winCenter.x;
        int y0 = (int) (((y * chunkSize) - yOffset) * cellSize) + winCenter.y;
        paintCells(g, x0, y0);
        paintGrid(g, x0, y0);
    }

    public void paintChunkOutlines(Graphics g, int x0, int y0) {
        int chunkWidth = (int) (chunkSize * cellSize);
        int chunkHeight = (int) (chunkSize * cellSize);
        g.drawRect(x0, y0, chunkWidth, chunkHeight);
    }

    public void paintCells(Graphics g, int x0, int y0) {
        g.setColor(World.C3);
        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                if (grid[x][y] == 1) {
                    int tx0 = (int) (x0 + (x * cellSize));
                    int ty0 = (int) (y0 + (y * cellSize));
                    g.fillRect(tx0, ty0, (int) cellSize, (int) cellSize);
                }
            }
        }
    }

    public void paintGrid(Graphics g, int x0, int y0) {
        g.setColor(World.C4);
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                g.drawLine((int) (x0 + i * cellSize), y0, (int) (x0 + i * cellSize), (int) (y0 + chunkSize * cellSize));
                g.drawLine(x0, (int) (y0 + j * cellSize), (int) (x0 + chunkSize * cellSize), (int) (y0 + j * cellSize));
            }
        }
    }

    public static void updateSize(Dimension dimension) {
        cellSize = dimension.width / zoom;
        windowSize = dimension;
        winCenter = new Point(dimension.width/2, dimension.height/2);
        Player.updateRadius(cellSize / 4);
    }

    public static void updateZoom(int dz) {
        zoom += dz;
        if (zoom > maxZoom) {
            zoom = maxZoom;
        }
        if (zoom < minZoom) {
            zoom = minZoom;
        }
        cellSize = windowSize.width / zoom;
        Player.updateRadius(cellSize / 4);
    }

    public void updateCell(int x, int y) {
        if (x >= Chunk.chunkSize || y >= Chunk.chunkSize) {
            return;
        }
        if (this.grid[x][y] == 0) {
            this.grid[x][y] = 1;
        } else {
            this.grid[x][y] = 0;
        }
    }
}
