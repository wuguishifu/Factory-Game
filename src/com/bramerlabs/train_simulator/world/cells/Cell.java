package com.bramerlabs.train_simulator.world.cells;

import com.bramerlabs.train_simulator.world.cells.structures.Struct;
import com.bramerlabs.train_simulator.world.cells.tiles.Tile;

import java.awt.*;

public class Cell {

    private Tile tile;
    private Struct struct;

    public Cell(Tile ground) {
        this.tile = ground;
    }

    public void setStruct(Struct struct) {
        this.struct = struct;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return this.tile;
    }

    public Struct getStruct() {
        return this.struct;
    }

    public void paint(Graphics g, int x, int y, int cellSize) {
        if (tile != null) {
            tile.paint(g, x, y, cellSize);
        }
        if (struct != null) {
            struct.paint(g, x, y, cellSize);
        }
    }

}
