package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.World;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {

    private int wheelRotation;

    private Main main;
    private World world;
    private Player player;

    public MouseWheelListener(Main main, World world, Player player) {
        this.main = main;
        this.world = world;
        this.player = player;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        main.updateZoom(mouseWheelEvent.getWheelRotation());
    }

    public int getWheelRotation() {
        return this.wheelRotation;
    }

}
