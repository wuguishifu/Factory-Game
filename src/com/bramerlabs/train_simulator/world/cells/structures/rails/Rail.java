package com.bramerlabs.train_simulator.world.cells.structures.rails;

import com.bramerlabs.train_simulator.world.cells.structures.Struct;

import java.util.HashMap;

public abstract class Rail extends Struct {

    HashMap<String, Integer> metaData;

    public static String TYPE = "type";

    public Rail(int ID) {
        super(ID);

        metaData = new HashMap<>();
    }

    @Override
    public void setMeta(String key, int value) {
        metaData.remove(key);
        metaData.put(key, value);
    }

    @Override
    public int getMeta(String key) {
        return(metaData.get(key));
    }
}
