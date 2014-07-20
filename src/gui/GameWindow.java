package gui;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
    private MenuBar menuBar; // holds menus for game options and game status
    private Map map; // displays the game map where most action takes place
    private IntelPanel intel; // displays information of selected cell and detailed game status

    public GameWindow(String name) throws IOException {
        setTitle(name);
        setFocusable(false);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // determine screen size (will be used to support multiple resolutions)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println("width " + width);
        System.out.println("height " + height);
        // screen size code ends here

        menuBar = new MenuBar();
        intel = new IntelPanel();
        map = new Map();
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
        lowerPanel.setBorder(new LineBorder(Color.BLUE, 1));
        lowerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 858));
        lowerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        lowerPanel.add(intel);
        lowerPanel.add(map);
        add(menuBar);
        add(lowerPanel);

        // Causes the ActionMenu to stay at one spot no matter where or how the JFrame or its components are relocated
        addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                map.relocateActionMenu();
            }
            @Override
            public void componentResized(ComponentEvent e) {
                map.relocateActionMenu();
            }
        });

        // Causes minimize of JFrame to also minimize the ActionMenu
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                map.setActionMenuVisibleState(false);
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
                if (map.isActiveActionMenu()) {
                    map.setActionMenuVisibleState(true);
                }
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
                map.setActionMenuVisibleState(false);
            }
            @Override
            public void windowActivated(WindowEvent e) {
                if (map.isActiveActionMenu()) {
                    map.setActionMenuVisibleState(true);
                }
            }
        });
    }

    // Setting map (a JPanel) focusable allows its key listener to work
    public void setMapFocus() {
        map.requestFocusInWindow();
    }
}
