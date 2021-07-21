package com.bramerlabs.train_simulator.world.title;

import com.bramerlabs.engine.graphics.Material;

import java.util.HashMap;

public class Tile {

    public static HashMap<Integer, Material> tileSet;

    private int type;

    public Tile(int type) {
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static void generateTiles() {
        tileSet = new HashMap<>();
        tileSet.put(0, new Material("textures/grass.png"));
        tileSet.put(1, new Material("textures/dirt.png"));
    }

    public int getType() {
        return this.type;
    }
}