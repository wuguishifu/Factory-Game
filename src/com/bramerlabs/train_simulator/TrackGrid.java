package com.bramerlabs.train_simulator;

import java.awt.*;

public class TrackGrid {

    // the size of the grid boxes
    public static final int GRID_BOX_SIZE = 20;

    // the array of tracks on the screen
    private Track[][] tracks;

    // the size of the window
    private Dimension windowSize;

    /**
     * default constructor
     */
    public TrackGrid(Dimension windowSize) {
        this.windowSize = windowSize;
        int numTracksHorizontal = windowSize.width / GRID_BOX_SIZE;
        int numTracksVertical = windowSize.height / GRID_BOX_SIZE;
        tracks = new Track[numTracksHorizontal][numTracksVertical];
        for (int i = 0; i < numTracksHorizontal; i++) {
            for (int j = 0; j < numTracksVertical; j++) {
                tracks[i][j] = new Track(false);
            }
        }
    }

    /**
     * toggles a track at a certain location
     * @param x - the x position of the track to toggle
     * @param y - the y position of the track to toggle
     */
    public void toggleTrackAtLocation(int x, int y) {
        int scaledX = x / GRID_BOX_SIZE;
        int scaledY = y / GRID_BOX_SIZE;
        tracks[scaledX][scaledY].toggleTrack();
    }

    /**
     * paints the tracks
     * @param g - the graphics object handed down by java awt
     */
    public void paintTracks(Graphics g) {
        for (int i = 0; i < tracks.length; i++) {
            for (int j = 0; j < tracks[0].length; j++) {
                tracks[i][j].paint(g, i * GRID_BOX_SIZE, j * GRID_BOX_SIZE, GRID_BOX_SIZE, GRID_BOX_SIZE);
            }
        }
    }

}
