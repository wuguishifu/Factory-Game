package com.bramerlabs.train_simulator.world.cells.structures;

import java.awt.*;

public abstract class Struct {

    private final int id;

    public Struct(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public abstract void paint(Graphics g, int x, int y, int cellSize);

    public abstract void setMeta(String key, int value);

}
