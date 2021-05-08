package com.bramerlabs.train_simulator.world.grid;

import com.bramerlabs.train_simulator.world.tile.Tile;

import java.util.ArrayList;

public class TileGrid {

    ArrayList<Tile> tilesSaved;
    ArrayList<Tile> tilesLoaded;

    public TileGrid() {
        tilesSaved = new ArrayList<>();
        tilesLoaded = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        this.tilesSaved.add(tile);
    }

    public void deleteTile(Tile tile) {
        this.tilesSaved.remove(tile);
    }

    public void loadTile(Tile tile) {
        if (!tilesSaved.contains(tile)) {
            tilesSaved.add(tile);
        }
        tilesLoaded.add(tile);
    }

    public void unloadTile(Tile tile) {
        tilesLoaded.remove(tile);
    }

    public ArrayList<Tile> getLoadedTiles() {
        return this.tilesLoaded;
    }

    public ArrayList<Tile> getSavedTiles() {
        return this.tilesSaved;
    }

}
