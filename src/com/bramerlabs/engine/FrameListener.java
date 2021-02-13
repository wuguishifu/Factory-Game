package com.bramerlabs.engine;

import java.awt.event.KeyEvent;

public class FrameListener implements java.awt.event.KeyListener {

    private boolean frameShouldClose = false;

    // if the player is moving in any of the 4 directions: north, south, east, west
    private boolean[] moveDirections = new boolean[]{false, false, false, false};
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                frameShouldClose = true;
                break;
            case KeyEvent.VK_W:
                moveDirections[NORTH] = true;
                break;
            case KeyEvent.VK_S:
                moveDirections[SOUTH] = true;
                break;
            case KeyEvent.VK_A:
                moveDirections[WEST] = true;
                break;
            case KeyEvent.VK_D:
                moveDirections[EAST] = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_W:
                moveDirections[NORTH] = false;
                break;
            case KeyEvent.VK_S:
                moveDirections[SOUTH] = false;
                break;
            case KeyEvent.VK_A:
                moveDirections[WEST] = false;
                break;
            case KeyEvent.VK_D:
                moveDirections[EAST] = false;
                break;
        }
    }

    /**
     * getter method
     * @return - a boolean representing if the frame should close
     */
    public boolean frameShouldClose() {
        return frameShouldClose;
    }

    /**
     * check if the player should be moving in a specific direction
     * @param direction - the direction
     * @return - if the player should be moving in that direction
     */
    public boolean isMovingInDirection(int direction) {
        return moveDirections[direction];
    }

}
