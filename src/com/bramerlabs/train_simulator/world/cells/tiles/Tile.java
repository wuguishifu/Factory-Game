package com.bramerlabs.train_simulator.world.cells.tiles;

import java.awt.*;

public abstract class Tile {

    private final int id;

    public Tile(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public abstract void paint(Graphics g, int x, int y, int cellSize);

    public abstract void setMeta(String key, int value);

}
