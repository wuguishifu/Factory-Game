package com.bramerlabs.engine;

import com.bramerlabs.train_simulator.Player;
import com.bramerlabs.train_simulator.TrackGrid;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    // a pointer to the player object
    private Player player;

    // a pointer to the track grid object
    private TrackGrid trackGrid;

    /**
     * main constructor
     */
    public Panel(Dimension size) {
        super();
        this.setSize(size);
    }

    /**
     * adds a pointer for the player object
     * @param player - the player object pointer
     */
    public void addPlayer(Player player) {
        this.player = player;
    }

    public void addTrackGrid(TrackGrid trackGrid) {
        this.trackGrid = trackGrid;
    }

    /**
     * paints the scene
     * @param g - the graphics component created by java.awt
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        trackGrid.paintTracks(g);
        player.paint(g);
    }
}