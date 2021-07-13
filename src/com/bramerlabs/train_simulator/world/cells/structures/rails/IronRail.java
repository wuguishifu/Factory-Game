package com.bramerlabs.train_simulator.world.cells.structures.rails;

import java.awt.*;

public class IronRail extends Rail {

    public static final int ID = 2;

    public IronRail() {
        super(ID);
    }

    @Override
    public void paint(Graphics g, int x, int y, int c) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(71, 69, 66));
        g2d.setStroke(new BasicStroke(3));
        int h = c/2;
        if (metaData.get(TYPE) == 0) {
            g2d.drawLine(x + h, y + h, x + h, y + h);
        }
        if ((metaData.get(TYPE) & 1) == 1) {
            g2d.drawLine(x + h, y + h, x + c, y + h);
        }
        if ((metaData.get(TYPE) & 2) == 2) {
            g2d.drawLine(x + h, y, x + h, y + h);
        }
        if ((metaData.get(TYPE) & 4) == 4) {
            g2d.drawLine(x, y + h, x + h, y + h);
        }
        if ((metaData.get(TYPE) & 8) == 8) {
            g2d.drawLine(x + h, y + h, x + h, y + c);
        }
    }

}
