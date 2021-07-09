package com.bramerlabs.train_simulator.main;

import com.bramerlabs.train_simulator.io.KeyListener;
import com.bramerlabs.train_simulator.io.MouseListener;
import com.bramerlabs.train_simulator.io.MouseMotionListener;
import com.bramerlabs.train_simulator.world.Chunk;
import com.bramerlabs.train_simulator.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Main implements Runnable {

    private JFrame frame;
    private JPanel panel;

    private KeyListener keyListener;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;

    private World world;
    private Chunk chunk;

    private boolean run = true;

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Thread main = new Thread(this, "Train Simulator");
        main.start();
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        init();
        while (run) {
            update();
            render();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        close();
    }

    public void init() {
        frame = new JFrame();
        panel = new JPanel();

        keyListener = new KeyListener(this);
        mouseListener = new MouseListener(this);
        mouseMotionListener = new MouseMotionListener(this);

        Dimension windowSize = new Dimension(800, 600);
        panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                world.paint(g);
            }
        };
        panel.setPreferredSize(windowSize);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.addKeyListener(keyListener);
        panel.addMouseListener(mouseListener);
        panel.addMouseMotionListener(mouseMotionListener);

        world = new World();
        chunk = new Chunk(0, 0);
        world.addChunk(chunk);
        world.showChunk(chunk);
    }

    public void update() {
        if (keyListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            run = false;
        }

        keyListener.update();
        mouseListener.update();
    }

    public void render() {
        panel.repaint();
    }

    public void close() {
        frame.dispose();
    }
}