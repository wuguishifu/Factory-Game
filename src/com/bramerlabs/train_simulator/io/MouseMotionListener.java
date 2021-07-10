package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.World;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {

    private Main main;
    private World world;
    private Player player;

    private int xPos;
    private int yPos;

    public MouseMotionListener(Main main, World world, Player player) {
        this.main = main;
        this.world = world;
        this.player = player;
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
