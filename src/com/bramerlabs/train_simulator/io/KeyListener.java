package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    private Main main;

    private final boolean[] keysPressed;
    private final boolean[] keysPressedLast;

    public KeyListener(Main main) {
        super();
        this.main = main;

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

    public boolean isKeyTyped(int keyCode) {
        return keysPressed[keyCode] && !keysPressedLast[keyCode];
    }

    public void update() {
        System.arraycopy(keysPressed, 0, keysPressedLast, 0, keysPressed.length);
    }

}