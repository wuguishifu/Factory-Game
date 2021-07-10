package com.bramerlabs.train_simulator.io;

import com.bramerlabs.train_simulator.engine.Vector2f;
import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.Chunk;
import com.bramerlabs.train_simulator.world.World;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {

    private Main main;
    private World world;
    private Player player;

    private final boolean[] buttonsDown;
    private final boolean[] buttonsDownLast;

    public MouseListener(Main main, World world, Player player) {
        super();
        this.main = main;
        this.world = world;
        this.player = player;

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
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            Vector2f mousePosition = new Vector2f(mouseEvent.getX(), mouseEvent.getY());
            mousePosition = Vector2f.add(mousePosition, -main.winCenter.x, -main.winCenter.y);
            mousePosition = Vector2f.scale(mousePosition, 1/(Chunk.cellSize));
            world.updateCell(mousePosition, player.position);
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
