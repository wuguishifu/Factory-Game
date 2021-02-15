package com.bramerlabs.engine;

import com.bramerlabs.train_simulator.TrackGrid;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseListener implements MouseMotionListener, java.awt.event.MouseListener {

    // a pointer to the grid of tracks
    private TrackGrid trackGrid;

    // the x and y position of the mouse
    private int x, y;

    /**
     * default constructor
     */
    public MouseListener(TrackGrid trackGrid) {
        this.trackGrid = trackGrid;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        trackGrid.toggleTrackAtLocation(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.x = mouseEvent.getX();
        this.y = mouseEvent.getY();
    }
}
