package com.bramerlabs.train_simulator.world;

import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.cells.Cell;
import com.bramerlabs.train_simulator.world.cells.structures.Struct;
import com.bramerlabs.train_simulator.world.cells.structures.rails.Rail;
import com.bramerlabs.train_simulator.world.cells.tiles.ground.EmptyGround;

import java.awt.*;

public class Chunk {

    private final int x, y;

    Cell[][] grid;

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

        grid = new Cell[chunkSize][chunkSize];
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize; j++) {
                grid[i][j] = new Cell(new EmptyGround());
            }
        }
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
        paintGrid(g, x0, y0);
        paintCells(g, x0, y0);
    }

    public void paintCells(Graphics g, int x0, int y0) {
        g.setColor(World.C3);
        for (int y = 0; y < chunkSize; y++) {
            for (int x = 0; x < chunkSize; x++) {
                grid[x][y].paint(g, (int) (x0 + x * cellSize), (int) (y0 + y * cellSize), (int) cellSize);
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

    public void updateCell(int x, int y, Struct struct) {
        grid[x][y].setStruct(struct);
        refreshGrid();
    }

    public void refreshGrid() {
        for (int x = 0; x < chunkSize; x++) {
            for (int y = 0; y < chunkSize; y++) {
                if (grid[x][y].getStruct() instanceof Rail) {
                    int id = grid[x][y].getStruct().id();
                    boolean U = false, D = false, L = false, R = false;
                    if (x > 0 && grid[x - 1][y].getStruct() instanceof Rail) {
                        if (grid[x - 1][y].getStruct().id() == id) {
                            L = true;
                        }
                    }
                    if (x < chunkSize - 1 && grid[x + 1][y].getStruct() instanceof Rail) {
                        if (grid[x + 1][y].getStruct().id() == id) {
                            R = true;
                        }
                    }
                    if (y > 0 && grid[x][y - 1].getStruct() instanceof Rail) {
                        if (grid[x][y - 1].getStruct().id() == id) {
                            U = true;
                        }
                    }
                    if (y < chunkSize - 1 && grid[x][y + 1].getStruct() instanceof Rail) {
                        if (grid[x][y + 1].getStruct().id() == id) {
                            D = true;
                        }
                    }
                    int type = (R ? 1 : 0) + (U ? 2 : 0) + (L ? 4 : 0) + (D ? 8 : 0);
                    grid[x][y].getStruct().setMeta(Rail.TYPE, type);
                }
            }
        }
    }
}
