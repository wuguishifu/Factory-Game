package com.bramerlabs.train_simulator.world.cells.tiles.ground;

import com.bramerlabs.train_simulator.world.cells.tiles.Tile;

import java.awt.*;

public class EmptyGround extends Tile {

    private static final int id = 0;

    public EmptyGround() {
        super(id);
    }

    @Override
    public void setMeta(String key, int value) {

    }

    @Override
    public void paint(Graphics g, int x, int y, int cellSize) {

    }
}
