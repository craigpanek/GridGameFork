package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class IntelPanel extends JPanel {
    // Player information
    private JLabel jlblTurn;
    private int turnCount = 0;
    // Cell information
    private JLabel jlblCoordinates;
    private int xPos = 0, yPos = 0;
    private JLabel jlblterrain;
    private JLabel jlblstructure;
    private JLabel jlblgameUnit;
    private JLabel jlbldefenseRating;
    private JLabel jlblstaminaCost;

    public IntelPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, Integer.MAX_VALUE));
        setMinimumSize(new Dimension(300, 1));
        setMaximumSize(new Dimension(300, Integer.MAX_VALUE));
        setBorder(new LineBorder(Color.DARK_GRAY, 1));
        // Player Information
        JLabel jlblPlayer = new JLabel("Player Intel");
        jlblTurn = new JLabel("    Turn " + turnCount);
        // Cell information
        JLabel jlblPosition = new JLabel("Position Intel");
        jlblCoordinates = new JLabel("    Coordinates: (" + xPos + ", "+ yPos + ")");
        jlblterrain = new JLabel("    Terrain: sea");
        jlblstructure = new JLabel("    Structure: NA");
        jlblgameUnit = new JLabel("    Game Unit: NA");
        jlbldefenseRating = new JLabel("    Defense Rating: *");
        jlblstaminaCost = new JLabel("    Stamina Cost: 1");

        add(jlblPlayer);
        add(jlblTurn);
        add(jlblPosition);
        add(jlblCoordinates);
        add(jlblterrain);
        add(jlblstructure);
        add(jlblgameUnit);
        add(jlbldefenseRating);
        add(jlblstaminaCost);
    }
}
