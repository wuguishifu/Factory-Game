package com.bramerlabs.train_simulator;

import java.awt.*;

public class Player {

    // the location of the player
    public int x, y;

    // movement variables for the player
    public static final int DX = 10;
    public static final int DY = 10;

    // the size of the player
    public final static int width = 20, height = 20;

    // the color of the player
    private final Color color = Color.BLACK;

    /**
     * default constructor for specified location
     * @param x - the x location of the player
     * @param y - the y location of the player
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * moves the player
     * @param dx - the change in x position
     * @param dy - the change in y position
     */
    public void movePlayer(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * paints the player onto the screen
     * @param g - the graphics object handed down by java awt
     */
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(this.x - width / 2, this.y - height / 2, width, height);
    }

}
