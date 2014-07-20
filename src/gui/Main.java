package gui;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame gameWindow = new GameWindow("Peter and Craig's Fantasy Game!");
        // Windowed mode
        gameWindow.setSize(1250, 725);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setResizable(true);
        gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //gameWindow.setUndecorated(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);
        // Full-screen mode (poor performance)
        //GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(gameWindow); // full screen mode
        ((GameWindow) gameWindow).setMapFocus();
    }
}
