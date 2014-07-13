/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Map extends JFrame {
	private Cell cells[][];
	private Cell previousCell;
	private static ActionMenu actionMenu;
	private JScrollPane scrollPane;
	private JPanel cellPanel;
	final int KEY_MOVEMENT = 32;

	public Map(String name) throws IOException {
		this.setTitle(name);
		cellPanel = new JPanel();
		actionMenu = new ActionMenu();
		cellPanel.setLayout(new GridLayout(12, 20));
		constructMap();
		fillMap1();
		scrollPane = new JScrollPane(cellPanel);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(scrollPane);
		panel.addKeyListener(new GameKeyListener());
		panel.setFocusable(true);
		add(panel);
	}

	public void constructMap() {
		cells = new Cell[12][20];

		for (int x = 0; x < cells.length; x++) {
			for (int y = 0; y < cells[0].length; y++) {
				Cell cell = new Cell(new ImageIcon("terrain-plains-64.png"));
				MouseButtonListener listener = new MouseButtonListener(x, y,
						cell);
				cell.addMouseListener(listener);
				cells[x][y] = cell;
				cellPanel.add(cell);
			}
		}
		previousCell = cells[0][0];
		previousCell.setSelected(true);
		cells[0][0].setBorder(new LineBorder(Color.BLUE));
	}

	public void setActionMenu(Point point) {
		actionMenu.setLocation(point);
	}

	class GameKeyListener implements KeyListener {

		public void keyPressed(KeyEvent event) {
			String key = KeyStroke.getKeyStrokeForEvent(event).toString();
			key = key.replace("pressed ", "");

			int yPos = scrollPane.getVerticalScrollBar().getValue();
			int xPos = scrollPane.getHorizontalScrollBar().getValue();
			int minY = scrollPane.getVerticalScrollBar().getMinimum();
			int maxY = scrollPane.getVerticalScrollBar().getMaximum();
			int minX = scrollPane.getHorizontalScrollBar().getMinimum();
			int maxX = scrollPane.getHorizontalScrollBar().getMaximum();

			// Process key
			if (key.equals("UP")) {
				scrollPane.getVerticalScrollBar().setValue(
						Math.max(yPos - KEY_MOVEMENT, minY));
			}
			if (key.equals("DOWN")) {
				scrollPane.getVerticalScrollBar().setValue(
						Math.min(yPos + KEY_MOVEMENT, maxY));
			}
			if (key.equals("LEFT")) {
				scrollPane.getHorizontalScrollBar().setValue(
						Math.max(xPos - KEY_MOVEMENT, minX));
			}
			if (key.equals("RIGHT")) {
				scrollPane.getHorizontalScrollBar().setValue(
						Math.min(xPos + KEY_MOVEMENT, maxX));
			}
		}

		// unused methods
		public void keyReleased(KeyEvent event) {}
		public void keyTyped(KeyEvent event) {}
	}

	class MouseButtonListener extends MouseAdapter {
		private int x;
		private int y;
		private Cell cell;

		public MouseButtonListener(int x, int y, Cell cell) {
			this.x = x;
			this.y = y;
			this.cell = cell;
		}

		public void mousePressed(MouseEvent e) {
			actionMenu.setVisible(false);
			// clear previous cell
			if (!cell.isSelected()) {
				previousCell.setBorder(new LineBorder(new Color(15, 130, 20)));
			}
			// deselected previous cell
			previousCell.setSelected(false);
			// select new cell and mark as previous cell
			previousCell = cell;
			cell.setSelected(true);

			// Display coordinates in console text area
			Point selectedPoint = new Point(cell.getX(), cell.getY());
			System.out.print(selectedPoint + " ");
			System.out.println("CellX = "
					+ ((int) (selectedPoint.getX() / cell.getWidth()) + 1)
					+ ", CellY = "
					+ ((int) (selectedPoint.getY() / cell.getHeight()) + 1));

			// Display actionMenu if right mouse button pressed
			if (e.getButton() == MouseEvent.BUTTON3) {
				Point point = new Point(cell.getLocationOnScreen());
				actionMenu.
				setLocation((int) point.getX() + 50,
						(int) point.getY() + 50);
				actionMenu.setVisible(true);
			}
		}

		public void mouseEntered(MouseEvent e) {
			// mark selected cells dark green and unselected cells
			// light green
			if (cell.isSelected()) {
				cell.setBorder(new LineBorder(new Color(0, 230, 0)));
			} else {
				cell.setBorder(new LineBorder(Color.GREEN));
			}
		}

		public void mouseExited(MouseEvent e) {
			// when mouse exits, cell color reverts to appropriate
			// state
			if (cell.isSelected()) {
				cell.setBorder(new LineBorder(Color.BLUE));
			} else {
				cell.setBorder(new LineBorder(new Color(15, 130, 20)));
			}
			// if mouse is outside game board then remove actionMenu
			if(cellPanel.getMousePosition() == null) {
				actionMenu.setVisible(false);
			}
		}
	}

	public void fillMap1() throws IOException {
		cells[0][0].setTerrain(new ImageIcon("terrain-sea-64.png"));
		cells[0][1].setTerrain(new ImageIcon("terrain-sea-64.png"));
		cells[0][2].setTerrain(new ImageIcon(
				"terrain-sea-shore-corner-se-64.png"));
		cells[0][3].setTerrain(new ImageIcon("terrain-sea-shore-south-64.png"));
		cells[0][4].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
		cells[0][5].setTerrain(new ImageIcon("woods64.png"));
		cells[0][6].setTerrain(new ImageIcon("woods64.png"));
		cells[0][7].setTerrain(new ImageIcon("woods64.png"));

		cells[1][0].setTerrain(new ImageIcon("terrain-sea-64.png"));
		cells[1][1].setTerrain(new ImageIcon(
				"terrain-sea-shore-corner-se-64.png"));
		cells[1][2].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
		cells[1][4].setTerrain(new ImageIcon("woods64.png"));
		cells[1][5].setTerrain(new ImageIcon("woods64.png"));
		cells[1][6].setTerrain(new ImageIcon("woods64.png"));

		cells[2][0].setTerrain(new ImageIcon("terrain-sea-shore-south-64.png"));
		cells[2][1].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
		cells[2][5].setTerrain(new ImageIcon("woods64.png"));

		cells[3][7].setTerrain(new ImageIcon("woods64.png"));
		cells[4][7].setTerrain(new ImageIcon("woods64.png"));
		cells[4][8].setTerrain(new ImageIcon("woods64.png"));
		cells[5][6].setTerrain(new ImageIcon("woods64.png"));
		cells[5][7].setTerrain(new ImageIcon("woods64.png"));

		cells[6][6].setTerrain(new ImageIcon("woods64.png"));
		cells[6][7].setTerrain(new ImageIcon("woods64.png"));
		cells[7][6].setTerrain(new ImageIcon("woods64.png"));
		cells[7][5].setTerrain(new ImageIcon("woods64.png"));

		cells[1][11].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[1][12].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[2][10].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[2][11].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[2][12].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[3][11].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[3][12].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[3][13].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[4][11].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[4][12].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[4][13].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[5][12].setIcon(new ImageIcon("terrain-desert-64.png"));
		cells[5][13].setIcon(new ImageIcon("terrain-desert-64.png"));

		cells[10][10].drawUnit();
	}
}