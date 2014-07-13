/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class ActionMenu extends JWindow {
	private JButton move;
	private JButton attack;
	private JButton inventory;
	private JButton status;

	public ActionMenu() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		move = new JButton("move");
		attack = new JButton("attack");
		inventory = new JButton("inventory");
		status = new JButton("status");
		panel.add(move);
		panel.add(attack);
		panel.add(inventory);
		panel.add(status);
		add(panel);
		setSize(200, 128);
		getContentPane().setBackground(Color.GRAY);
		setAlwaysOnTop(true);

		// this listener has no effect
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				setVisible(false);
			}
		});
	}
}
