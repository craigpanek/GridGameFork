package gui;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class ActionMenu extends JWindow {
    private JButton move;
    private JButton attack;
    private JButton inventory;
    private JButton status;

    public ActionMenu() {
        //setOpacity(0.3f);
        //setBorder(new LineBorder(Color.RED, 2));
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.BLUE, 2));
        panel.setLayout(new GridLayout(2, 2));
        move = new JButton("move");
        move.setMargin(new Insets(0, 0, 0, 0));
        attack = new JButton("attack");
        attack.setMargin(new Insets(0, 0, 0, 0));
        inventory = new JButton("inventory");
        inventory.setMargin(new Insets(0, 0, 0, 0));
        status = new JButton("status");
        status.setMargin(new Insets(0, 0, 0, 0));
        panel.add(move);
        panel.add(attack);
        panel.add(inventory);
        panel.add(status);
        add(panel);
        setSize(150, 90);
        getContentPane().setBackground(Color.GRAY);
        setAlwaysOnTop(true);
        move.addMouseListener(new ActionMenuButtonMouseListener());
        attack.addMouseListener(new ActionMenuButtonMouseListener());
        inventory.addMouseListener(new ActionMenuButtonMouseListener());
        status.addMouseListener(new ActionMenuButtonMouseListener());
    }
    
    /** This listener brightens the actionMenu if the mouse is hovered
     *  over any of the actionMenu JButtons
    */
    class ActionMenuButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent arg0) {
            setOpacity(1.0f);
        }
        @Override
        public void mouseExited(MouseEvent arg0) {
            setOpacity(0.3f);
        }
    }
    
    class ActionButton extends JButton {
        // this will be the move, attack, inventory, status, and magic button
    }

    public void enableMove(boolean enable) {
        if (enable)
            move.setEnabled(true);
        else
            move.setEnabled(false);
    }

    public JButton getMoveButton() {
        return move;
    }
}
