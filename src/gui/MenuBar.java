package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class MenuBar extends JPanel {
    private JButton jbtMenu;

    public MenuBar() {
        jbtMenu = new JButton("Menu");
        jbtMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(new LineBorder(Color.BLUE, 1));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        add(jbtMenu);
    }
}
