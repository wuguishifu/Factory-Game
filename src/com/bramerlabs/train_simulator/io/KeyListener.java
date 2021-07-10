package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.World;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    private Main main;
    private World world;
    private Player player;

    private final boolean[] keysPressed;
    private final boolean[] keysPressedLast;

    public KeyListener(Main main, World world, Player player) {
        super();
        this.main = main;
        this.world = world;
        this.player = player;

        keysPressed = new boolean[128];
        keysPressedLast = new boolean[128];
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() <= 128) {
            keysPressed[keyEvent.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() <= 128) {
            keysPressed[keyEvent.getKeyCode()] = false;
        }
    }

    public boolean isKeyDown(int keyCode) {
        return keysPressed[keyCode];
    }

    public boolean isKeyPressed(int keyCode) {
        return keysPressed[keyCode] && !keysPressedLast[keyCode];
    }

    public boolean isKeyReleased(int keyCode) {
        return !keysPressed[keyCode] && keysPressedLast[keyCode];
    }

    public void update() {
        System.arraycopy(keysPressed, 0, keysPressedLast, 0, keysPressed.length);
    }

}