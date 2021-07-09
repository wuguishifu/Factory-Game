package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.main.Main;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private Main main;

    private final boolean[] buttonsDown;
    private final boolean[] buttonsDownLast;

    public MouseListener(Main main) {
        super();
        this.main = main;

        buttonsDown = new boolean[4];
        buttonsDownLast = new boolean[4];
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() <= 3) {
            buttonsDown[mouseEvent.getButton()] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() <= 3) {
            buttonsDown[mouseEvent.getButton()] = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    public boolean isButtonDown(int buttonCode) {
        return buttonsDown[buttonCode];
    }

    public boolean isButtonClicked(int buttonCode) {
        return buttonsDown[buttonCode] && !buttonsDownLast[buttonCode];
    }

    public void update() {
        System.arraycopy(buttonsDown, 0, buttonsDownLast, 0, buttonsDown.length);
    }
}
