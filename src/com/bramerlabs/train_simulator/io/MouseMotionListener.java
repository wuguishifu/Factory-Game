package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

    private Main main;

    private int xPos;
    private int yPos;

    public MouseMotionListener(Main main) {
        this.main = main;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.xPos = mouseEvent.getX();
        this.yPos = mouseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.xPos = mouseEvent.getX();
        this.yPos = mouseEvent.getY();
    }

    public Point getMousePos() {
        return new Point(xPos, yPos);
    }
}
