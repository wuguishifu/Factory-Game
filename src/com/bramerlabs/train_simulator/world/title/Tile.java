package com.bramerlabs.train_simulator.world.title;

import com.bramerlabs.engine.graphics.Material;
import com.bramerlabs.engine.math.vector.Vector2f;
import com.bramerlabs.engine.objects.shapes_2d.Square;

import java.util.HashMap;

public class Tile {

    public static HashMap<String, Square> tileSet;

    public Tile() {

    }

    public static void generateTiles() {
        tileSet.put("Default", new Square(new Material("textures/test.png"),
                new Vector2f(0), 0, new Vector2f(1)));
    }
}