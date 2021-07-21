package com.bramerlabs.train_simulator.world.title;

import com.bramerlabs.engine.graphics.Material;

import java.util.HashMap;

public class Tile {

    public static HashMap<Integer, Material> tileSet;
    public static final int numTextures = 5;

    private int type;

    public Tile(int type) {
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static void generateTiles() {
        tileSet = new HashMap<>();
        tileSet.put(0, new Material("textures/grass2.png"));
        tileSet.put(1, new Material("textures/grass.png"));
        tileSet.put(2, new Material("textures/dirt.png"));
        tileSet.put(3, new Material("textures/dirt2.png"));
        tileSet.put(4, new Material("textures/snow.png"));
    }

    public int getType() {
        return this.type;
    }

    public static void destroyTextures() {
        for (Material material : tileSet.values()) {
            material.destroy();
        }
    }

}