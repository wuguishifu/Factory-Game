package com.bramerlabs.train_simulator;

import java.awt.*;

public class Track {

    // the color of the track
    private static final Color color = Color.lightGray;

    private boolean isShown;

    /**
     * default constructor
     */
    public Track(boolean isShown) {
        this.isShown = isShown;
    }

    /**
     * toggles if the track is visible or not
     */
    public void toggleTrack() {
        this.isShown = !isShown;
    }

    /**
     * paints the track
     * @param g - the graphics object handed down by java awt
     * @param x - the x position of the track
     * @param y - the y position of the track
     * @param width - the width of the track
     * @param height - the height of the track
     */
    public void paint(Graphics g, int x, int y, int width, int height) {
        if (isShown) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        } else {
            g.setColor(color);
            g.drawRect(x, y, width, height);
        }
    }

}
