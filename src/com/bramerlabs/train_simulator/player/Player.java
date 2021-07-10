package com.bramerlabs.train_simulator.player;

import com.bramerlabs.train_simulator.engine.Vector2f;
import com.bramerlabs.train_simulator.main.Main;
import com.bramerlabs.train_simulator.world.World;

import java.awt.*;

public class Player {

    public Vector2f position;
    public Vector2f velocity, friction;
    public static final float acceleration = 0.02f;
    public static final float frictionMagnitude = 0.0085f;

    public static final float maxV = 0.3f;

    public boolean up, down, left, right;

    private static Point winCenter;

    public static float radius = 10.0f;

    public Player(float x, float y, Main main) {
        this.position = new Vector2f(x, y);
        this.velocity = new Vector2f(0, 0);
        this.friction = new Vector2f(0, 0);

        winCenter = new Point(main.windowSize.width/2, main.windowSize.height/2);
    }

    public void update() {
        // handle force
        if (up) {
            velocity = Vector2f.add(velocity, new Vector2f(0, -acceleration));
        }
        if (down) {
            velocity = Vector2f.add(velocity, new Vector2f(0, acceleration));
        }
        if (left) {
            velocity = Vector2f.add(velocity, new Vector2f(-acceleration, 0));
        }
        if (right) {
            velocity = Vector2f.add(velocity, new Vector2f(acceleration, 0));
        }
        if (Vector2f.length(velocity) > maxV) {
            velocity.normalize(maxV);
        }

        // handle friction
        if (velocity.length() > 0) {
            friction = Vector2f.normalize(Vector2f.subtract(new Vector2f(0, 0), velocity), frictionMagnitude);
            velocity = Vector2f.add(velocity, friction);
        }

        if (velocity.length() < 0.01f) {
            velocity = new Vector2f(0, 0);
        }

        // update position
        position = Vector2f.add(position, velocity);
    }

    public void paint(Graphics g) {
        g.setColor(World.C2);
        g.fillOval((int) (winCenter.x - radius), (int) (winCenter.y - radius),
                (int) (2 * radius), (int) (2 * radius));
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(World.C1);
        g2d.drawOval((int) (winCenter.x - radius), (int) (winCenter.y - radius),
                (int) (2 * radius), (int) (2 * radius));
        g2d.dispose();
    }

    public void updateSize(Dimension windowSize) {
        winCenter = new Point(windowSize.width/2, windowSize.height/2);
    }

    public static void updateRadius(float r) {
        radius = r;
    }

}
