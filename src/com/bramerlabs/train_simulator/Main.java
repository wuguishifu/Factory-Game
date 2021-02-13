package com.bramerlabs.train_simulator;

import com.bramerlabs.engine.Frame;
import com.bramerlabs.engine.FrameListener;
import com.bramerlabs.engine.MouseListener;
import com.bramerlabs.engine.Panel;

import java.awt.*;

public class Main {

    // the frame and panel used to display the graphics
    static Frame frame;
    static Panel panel;

    // listeners
    static FrameListener frameListener;

    // the size of the screen and window, used for centering
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension WINDOW_SIZE = new Dimension(SCREEN_SIZE.width/2, SCREEN_SIZE.height/2);
    private static final int SCREEN_WIDTH = SCREEN_SIZE.width;
    private static final int SCREEN_HEIGHT = SCREEN_SIZE.height;

    // the player
    private static Player player;

    // the tracks on screen
    private static TrackGrid trackGrid;

    /**
     * the main runnable
     * @param args - jvm arguments
     */
    public static void main(String[] args) {
        // initialize the window
        frame = new Frame(WINDOW_SIZE);
        panel = new Panel(WINDOW_SIZE);

        // create the listeners
        frameListener = new FrameListener();

        // add components to the main frame
        frame.add(panel);
        frame.addKeyListener(frameListener);

        // adjust the frame size
        frame.getContentPane().setPreferredSize(WINDOW_SIZE);
        frame.pack();
        frame.setResizable(false);

        // display the window
        frame.setVisible(true);

        // initialize the player
        player = new Player((SCREEN_WIDTH / 2 - Player.width) / 2, (SCREEN_HEIGHT / 2 - Player.height) / 2);

        // add the player to the panel
        panel.addPlayer(player);

        // make the tracks
        trackGrid = new TrackGrid(WINDOW_SIZE);

        // add the track grid to the panel
        panel.addTrackGrid(trackGrid);

        // make a mouse listener
        MouseListener mouseListener = new MouseListener(trackGrid);
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseListener);

        boolean frameShouldClose = false;
        while (!frameShouldClose) {
            update();
            paint();

            if (frameListener.frameShouldClose()) {
                frameShouldClose = true;
            }

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // destroy the frame after the game loop ends
        frame.dispose();
    }

    /**
     * updates the window
     */
    public static void update() {
        movePlayer();
    }

    /**
     * repaints the window
     */
    public static void paint() {
        panel.repaint();
    }

    /**
     * moves the player
     */
    public static void movePlayer() {
        if (frameListener.isMovingInDirection(FrameListener.NORTH)) {
            player.movePlayer(0, -Player.DY);
        }
        if (frameListener.isMovingInDirection(FrameListener.SOUTH)) {
            player.movePlayer(0,  Player.DY);
        }
        if (frameListener.isMovingInDirection(FrameListener.EAST)) {
            player.movePlayer( Player.DX, 0);
        }
        if (frameListener.isMovingInDirection(FrameListener.WEST)) {
            player.movePlayer(-Player.DX, 0);
        }
    }
}