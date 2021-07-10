package com.bramerlabs.train_simulator.main;

import com.bramerlabs.train_simulator.io.KeyListener;
import com.bramerlabs.train_simulator.io.MouseListener;
import com.bramerlabs.train_simulator.io.MouseMotionListener;
import com.bramerlabs.train_simulator.io.MouseWheelListener;
import com.bramerlabs.train_simulator.player.Player;
import com.bramerlabs.train_simulator.world.Chunk;
import com.bramerlabs.train_simulator.world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main implements Runnable {

    private JFrame frame;
    private JPanel panel;

    private KeyListener keyListener;
    private MouseListener mouseListener;
    private MouseMotionListener mouseMotionListener;
    private MouseWheelListener mouseWheelListener;

    private World world;
    private Player player;

    public static final Dimension defaultWindowSize = new Dimension(800, 600);
    public Dimension windowSize = defaultWindowSize;
    public static final Point defaultWindowCenter = new Point(defaultWindowSize.width/2, defaultWindowSize.height/2);
    public Point winCenter = defaultWindowCenter;

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
        world = new World();
        ArrayList<Chunk> chunks = new ArrayList<>();
        chunks.add(new Chunk(0, 0));
        chunks.add(new Chunk(0, -1));
        chunks.add(new Chunk(-1, 0));
        chunks.add(new Chunk(-1, -1));
        for (Chunk chunk : chunks) {
            world.addChunk(chunk);
            world.showChunk(new World.Key(chunk.getX(), chunk.getY()));
        }

        player = new Player(0, 0, this);
        frame = new JFrame();
        panel = new JPanel();

        keyListener = new KeyListener(this, world, player);
        mouseListener = new MouseListener(this, world, player);
        mouseMotionListener = new MouseMotionListener(this, world, player);
        mouseWheelListener = new MouseWheelListener(this, world, player);

        windowSize = new Dimension(800, 600);
        panel = new JPanel(){
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g);
                Main.this.paint(g);
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
        panel.addMouseWheelListener(mouseWheelListener);
    }

    public void update() {
        if (keyListener.isKeyDown(KeyEvent.VK_ESCAPE)) {
            run = false;
        }

        // handle moving
        if (keyListener.isKeyPressed(KeyEvent.VK_W)) {
            player.up = true;
        }
        if (keyListener.isKeyReleased(KeyEvent.VK_W)) {
            player.up = false;
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_A)) {
            player.left = true;
        }
        if (keyListener.isKeyReleased(KeyEvent.VK_A)) {
            player.left = false;
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_S)) {
            player.down = true;
        }
        if (keyListener.isKeyReleased(KeyEvent.VK_S)) {
            player.down = false;
        }
        if (keyListener.isKeyPressed(KeyEvent.VK_D)) {
            player.right = true;
        }
        if (keyListener.isKeyReleased(KeyEvent.VK_D)) {
            player.right = false;
        }

        keyListener.update();
        mouseListener.update();
        player.update();

        Dimension panelSize = panel.getSize();
        if (!windowSize.equals(panelSize)) {
            windowSize = panelSize;
            winCenter = new Point(windowSize.width/2, windowSize.height/2);
            player.updateSize(windowSize);
            world.updateSize(windowSize);
        }

    }

    public void paint(Graphics g) {
        world.paint(g, player.position.x, player.position.y);
        player.paint(g);
    }

    public void render() {
        panel.repaint();
    }

    public void close() {
        frame.dispose();
    }

    public void updateZoom(int dz) {
        Chunk.updateZoom(dz);
    }
}