package com.bramerlabs.engine;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    /**
     * main constructor
     */
    public Frame(Dimension size) {
        super();
        this.setSize(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // put the frame in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)screenSize.width / 4, (int)screenSize.height / 4);

    }

}
