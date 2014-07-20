package gui;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import terrain.Desert;
import terrain.Forest;
import terrain.Plain;
import terrain.Sea;
import units.GameUnit;
import units.Movable;
import units.Peasant;
import units.Position;
import units.TransparentUnit;

@SuppressWarnings("serial")
public class Map extends JPanel {
    private Cell cells[][];
    private Cell selectedCell;
    private static ActionMenu actionMenu;
    private boolean isActiveActionMenu;
    private JScrollPane scrollPane;
    private JPanel cellPanel;
    final int KEY_MOVEMENT = 32;
    private int xClick, yClick;
    private int tileSize = 64;
    private String action = "";
    private GameUnit gameUnitInTransit;
    private final int NUM_ROWS = 12;
    private final int NUM_COLS = 20;
    private boolean[][] gridOfValidLandingPoints;

    public Map() throws IOException {
        setLayout(new GridLayout());
        setBorder(new LineBorder(Color.BLUE, 1));
        setFocusable(true);
        cellPanel = new JPanel();
        actionMenu = new ActionMenu();
        cellPanel.setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
        cellPanel.setBorder(new LineBorder(Color.DARK_GRAY, 2));

        // Black padding added at the edges of the cell map
        JPanel blackVoid = new JPanel();
        blackVoid.setBackground(Color.BLACK);
        // +2 accounts for 1-2 pixel line borders
        // +1 is the extra 64 pixels to pad the edges
        int blackVoidHeight = (NUM_ROWS + 1) * (tileSize + 2);
        int blackVoidWidth = (NUM_COLS + 1) * (tileSize + 2);
        System.out.println("HEIGHT " + blackVoidHeight);
        System.out.println("WIDTH " + blackVoidWidth);
        blackVoid.setPreferredSize(new Dimension(blackVoidWidth,
                blackVoidHeight));
        blackVoid.setLayout(new GridBagLayout());
        blackVoid.add(cellPanel);
        // end

        constructMap();
        fillMap1();
        scrollPane = new JScrollPane(blackVoid);
        // "hidden" scrollbar
        scrollPane
        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        // "hidden" scrollbar
        scrollPane
        .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1000, 550)); // default size
        System.out.println("Panel size is " + getSize());
        addKeyListener(new GameKeyListener());
        add(scrollPane);
        isActiveActionMenu = false; // state of action menu

        actionMenu.getMoveButton().addActionListener(new ActionListener() {
            @Override
            // This method is performed when we successfully click to move a
            // gameUnit
            public void actionPerformed(ActionEvent arg0) {
                setActionMenuVisibleState(false);
                isActiveActionMenu = false;
                action = "move";
                gameUnitInTransit = selectedCell.getGameUnit();
                int distance = gameUnitInTransit.getStamina();
                try {
                    displayPossibleLandingPoints(selectedCell, distance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    /* Reset gameUnit on source cell to transparent (it won't
                     * affect the cells actual IMAGE (with the unit) yet) */
                    selectedCell.setGameUnit(new TransparentUnit(selectedCell
                            .getPosition()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void constructMap() throws IOException {
        cells = new Cell[NUM_ROWS][NUM_COLS];

        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                Cell cell = new Cell(new Plain(), row, col);
                cell.addMouseListener(new MouseButtonListener(row, col, cell));
                cell.addMouseMotionListener(new MouseDragMotionListener());
                cells[row][col] = cell;
                cellPanel.add(cell);
            }
        }
        selectedCell = cells[0][0];
        cells[0][0].setBorder(new LineBorder(Color.BLUE));
    }

    public void displayPossibleLandingPoints(Cell cell, int distance)
            throws IOException {
        int centerRow = cell.getRow();
        int centerCol = cell.getCol();
        gridOfValidLandingPoints = new boolean[NUM_ROWS][NUM_COLS];
        seekAdjacentLandingPoints(centerRow, centerCol, distance);
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {
                if (gridOfValidLandingPoints[row][col])
                    cells[row][col].glossCellForMove();
            }
        }
    }

    private void seekAdjacentLandingPoints(int row, int col, int distance) {
        distance--;
        if (distance < 0)
            return;
        for (int rowMod = -1; rowMod <= 1; rowMod++) {
            for (int colMod = -1; colMod <= 1; colMod++) {
                if(rowMod == 0 || colMod == 0) {
                    if(isValidLandingPoint(row + rowMod, col + colMod)) {
                        gridOfValidLandingPoints[row + rowMod][col + colMod] = true;
                        seekAdjacentLandingPoints(row + rowMod, col + colMod, distance);
                    }
                }
            }
        }
    }

    public boolean isValidLandingPoint(int row, int col) {
        if (     (row >= 0 && row < NUM_ROWS && col >= 0 && col < NUM_COLS)
                && (!(cells[row][col].getGameUnit() instanceof Movable)))
            return true;
        else
            return false;
    }

    public void clearPossibleLandingPoints() {
        for(int row = 0; row < NUM_ROWS; row++) {
            for(int col = 0; col < NUM_COLS; col++) {
                if(gridOfValidLandingPoints[row][col] == true)
                    // Get rid of landing point marker for this cell
                    cells[row][col].setDefaultTerrain();
            }
        }
    }
    // This method redraws the action menu in the appropriate location
    public void relocateActionMenu() {
        try {
            Point point = new Point(selectedCell.getLocationOnScreen());
            if (selectedCell.getRow() == 0 && selectedCell.getCol() == 0) {
                actionMenu.setLocation((int) point.getX() + 50,
                        (int) point.getY() + 50);
            } else if (selectedCell.getRow() == 19
                    && selectedCell.getCol() == 0) {
                actionMenu.setLocation((int) point.getX() + 14 - 150,
                        (int) point.getY() + 50);
            } else if (selectedCell.getRow() == 19
                    && selectedCell.getCol() == 11) {
                actionMenu.setLocation((int) point.getX() + 14 - 150,
                        (int) point.getY() + 14 - 90);
            } else if (selectedCell.getRow() == 0
                    && selectedCell.getCol() == 11) {
                actionMenu.setLocation((int) point.getX() + 50,
                        (int) point.getY() + 14 - 90);
            } else if (selectedCell.getRow() == 0) {
                actionMenu.setLocation((int) point.getX() + 50,
                        (int) point.getY() + 32 - 45);
            } else if (selectedCell.getRow() == 19) {
                actionMenu.setLocation((int) point.getX() + 14 - 150,
                        (int) point.getY() + 32 - 45);
            } else if (selectedCell.getCol() == 0) {
                actionMenu.setLocation((int) point.getX() + 32 - 75,
                        (int) point.getY() + 50);
            } else if (selectedCell.getCol() == 11
                    || selectedCell.getCol() == 10) {
                actionMenu.setLocation((int) point.getX() + 32 - 75,
                        (int) point.getY() + 14 - 90);
            } else {
                actionMenu.setLocation((int) point.getX() + 32 - 75,
                        (int) point.getY() + 50);
            }
        } catch (IllegalComponentStateException ex) {
            System.out.println("Illegal Component State Exception");
            // this only happens when the program starts
        }
    }

    public boolean isActiveActionMenu() {
        return isActiveActionMenu;
    }

    public void setActionMenuVisibleState(boolean state) {
        actionMenu.setVisible(state);
    }

    // This listener allows scrolling of pane using mouse dragging
    class MouseDragMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK) {
                double mx = e.getLocationOnScreen().getX();
                double my = e.getLocationOnScreen().getY();
                int xPixelShift = (int) (xClick - mx);
                int yPixelShift = (int) (yClick - my);
                xClick = (int) mx;
                yClick = (int) my;
                scrollPane.getHorizontalScrollBar().setValue(
                        scrollPane.getHorizontalScrollBar().getValue()
                        + xPixelShift);
                scrollPane.getVerticalScrollBar().setValue(
                        scrollPane.getVerticalScrollBar().getValue()
                        + yPixelShift);
            }
        }
    }

    // This listener processes key strokes
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
            relocateActionMenu();
        }

        // unused methods
        public void keyReleased(KeyEvent event) {
        }

        public void keyTyped(KeyEvent event) {
        }
    }

    // This listener is attached to every cell
    class MouseButtonListener extends MouseAdapter {
        private int x;
        private int y;
        private Cell cell;

        public MouseButtonListener(int y, int x, Cell cell) {
            this.y = y;
            this.x = x;
            this.cell = cell;
        }

        public void mousePressed(MouseEvent e) {
            // These 2 lines used for MouseDragMotionListener
            xClick = (int) e.getLocationOnScreen().getX();
            yClick = (int) e.getLocationOnScreen().getY();

            // These 2 lines prevent some bugs showing with actionMenu
            setActionMenuVisibleState(false);
            isActiveActionMenu = false;

            // clear previous cell if a new cell was clicked on
            if (cell != selectedCell) {
                selectedCell.setBorder(new LineBorder(new Color(15, 130, 20)));
            }

            // Do this if currently in the process of moving a gameUnit
            if (action.equals("move")) {
                clearPossibleLandingPoints();
                if ((e.getButton() == MouseEvent.BUTTON1)
                        && (!(cell.getGameUnit() instanceof Movable))
                        && (gridOfValidLandingPoints[cell.getRow()][cell.getCol()])) {
                    try {
                        // Redraw the source cell without the gameUnit
                        selectedCell.setDefaultTerrain();

                        // Redraw gameUnit onto new cell (blends the images)
                        cell.drawUnit(gameUnitInTransit);
                    } catch (IOException e1) {
                    }
                } else {
                    /* wrong button pressed, so reset the source cells
                     * gameUnit back to what it was originally */
                    selectedCell.setGameUnit(gameUnitInTransit);
                }
                action = "";
            }

            // now selectedCell is updated to whatever cell was just clicked on
            selectedCell = cell;

            // Set move button on actionMenu appropriately
            if (cell.getGameUnit() instanceof Movable) {
                actionMenu.enableMove(true);
            } else {
                actionMenu.enableMove(false);
            }

            // Display actionMenu if right mouse button pressed
            if (e.getButton() == MouseEvent.BUTTON3) {
                isActiveActionMenu = true;
                Point point = new Point(cell.getLocationOnScreen());
                actionMenu.setOpacity(1.0f);
                if (x == 0 && y == 0) {
                    actionMenu.setLocation((int) point.getX() + 50,
                            (int) point.getY() + 50);
                } else if (x == 19 && y == 0) {
                    actionMenu.setLocation((int) point.getX() + 14 - 150,
                            (int) point.getY() + 50);
                } else if (x == 19 && y == 11) {
                    actionMenu.setLocation((int) point.getX() + 14 - 150,
                            (int) point.getY() + 14 - 90);
                } else if (x == 0 && y == 11) {
                    actionMenu.setLocation((int) point.getX() + 50,
                            (int) point.getY() + 14 - 90);
                } else if (x == 0) {
                    actionMenu.setLocation((int) point.getX() + 50,
                            (int) point.getY() + 32 - 45);
                } else if (x == 19) {
                    actionMenu.setLocation((int) point.getX() + 14 - 150,
                            (int) point.getY() + 32 - 45);
                } else if (y == 0) {
                    actionMenu.setLocation((int) point.getX() + 32 - 75,
                            (int) point.getY() + 50);
                } else if (y == 11 || y == 10) {
                    actionMenu.setLocation((int) point.getX() + 32 - 75,
                            (int) point.getY() + 14 - 90);
                } else {
                    actionMenu.setLocation((int) point.getX() + 32 - 75,
                            (int) point.getY() + 50);
                }
                setActionMenuVisibleState(true);
            }
        }

        public void mouseEntered(MouseEvent e) {
            // mark selected cells dark green and unselected cells light green
            if (cell == selectedCell)
                cell.setBorder(new LineBorder(new Color(0, 230, 0)));
            else
                cell.setBorder(new LineBorder(Color.GREEN));
        }

        public void mouseExited(MouseEvent e) {
            // when mouse exits, cell color reverts to appropriate state
            if (cell == selectedCell)
                cell.setBorder(new LineBorder(Color.BLUE));
            else
                cell.setBorder(new LineBorder(new Color(15, 130, 20)));
        }
    }

    public void fillMap1() throws IOException {
        cells[0][0].setTerrain(new Sea());
        cells[0][1].setTerrain(new Sea());
        cells[0][2].setTerrain(new ImageIcon("terrain-sea-shore-corner-se-64.png"));
        cells[0][3].setTerrain(new ImageIcon("terrain-sea-shore-south-64.png"));
        cells[0][4].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
        cells[0][5].setTerrain(new Forest());
        cells[0][6].setTerrain(new Forest());
        cells[0][7].setTerrain(new Forest());

        cells[1][0].setTerrain(new Sea());
        cells[1][1].setTerrain(new ImageIcon("terrain-sea-shore-corner-se-64.png"));
        cells[1][2].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
        cells[1][4].setTerrain(new Forest());
        cells[1][5].setTerrain(new Forest());
        cells[1][6].setTerrain(new Forest());

        cells[2][0].setTerrain(new ImageIcon("terrain-sea-shore-south-64.png"));
        cells[2][1].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));
        cells[2][5].setTerrain(new Forest());

        cells[3][7].setTerrain(new Forest());
        cells[4][7].setTerrain(new Forest());
        cells[4][8].setTerrain(new Forest());
        cells[5][6].setTerrain(new Forest());
        cells[5][7].setTerrain(new Forest());

        cells[6][6].setTerrain(new Forest());
        cells[6][7].setTerrain(new Forest());
        cells[7][6].setTerrain(new Forest());
        cells[7][5].setTerrain(new Forest());

        cells[1][11].setTerrain(new Desert());
        cells[1][12].setTerrain(new Desert());
        cells[2][10].setTerrain(new Desert());
        cells[2][11].setTerrain(new Desert());
        cells[2][12].setTerrain(new Desert());
        cells[3][11].setTerrain(new Desert());
        cells[3][12].setTerrain(new Desert());
        cells[3][13].setTerrain(new Desert());
        cells[4][11].setTerrain(new Desert());
        cells[4][12].setTerrain(new Desert());
        cells[4][13].setTerrain(new Desert());
        cells[5][12].setTerrain(new Desert());
        cells[5][13].setTerrain(new Desert());

        cells[8][2].setTerrain(new ImageIcon("terrain-sea-shore-nw-64.png"));
        cells[8][3].setTerrain(new ImageIcon("terrain-sea-shore-ne-64.png"));
        cells[9][2].setTerrain(new ImageIcon("terrain-sea-shore-sw-64.png"));
        cells[9][3].setTerrain(new ImageIcon("terrain-sea-shore-corner-i-iii-64.png"));
        cells[9][4].setTerrain(new ImageIcon("terrain-sea-shore-corner-iv-shore-north-64.png"));
        cells[9][5].setTerrain(new ImageIcon( "terrain-sea-shore-three-edges-open-west-64.png"));
        cells[10][3].setTerrain(new ImageIcon("terrain-sea-shore-sw-64.png"));
        cells[10][4].setTerrain(new ImageIcon("terrain-sea-shore-se-64.png"));

        // drawUnit combines the gameUnit passed in with the cells terrainIcon
        cells[2][2].drawUnit(new Peasant(new Position(0, 0)));
        cells[1][3].drawUnit(new Peasant(new Position(0, 0)));
    }
}