package gui;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import terrain.Terrain;
import units.GameUnit;
import units.Position;
import units.TransparentUnit;

@SuppressWarnings("serial")
public class Cell extends JLabel {
    private Position position;
    private Terrain terrain;
    //private Structure structure;
    private GameUnit gameUnit;

    public Cell(Terrain terrain, int row, int col) throws IOException {
        this.terrain = terrain;
        setIcon(new ImageIcon(terrain.getTerrainImage()));
        setBorder(new LineBorder(new Color(15, 130, 20)));
        setPosition(new Position(row, col));
        // creates a default transparent gameUnit
        gameUnit = new TransparentUnit(position);
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
        setIcon(new ImageIcon(terrain.getTerrainImage()));
    }

    // Overloaded for image icons
    public void setTerrain(ImageIcon imageIcon) {
        terrain.setTerrainImage(imageIcon);
        setIcon(imageIcon);
    }

    public void setGameUnit(GameUnit gameUnit) {
        this.gameUnit = gameUnit;
    }

    public GameUnit getGameUnit() {
        return gameUnit;
    }

    public void setDefaultTerrain() {
        setIcon(new ImageIcon(terrain.getTerrainImage()));
    }

    /**
     * This method combines the gameUnit passed in with the terrainIcon and
     * creates a combined icon to display (gameUnit updates to what was passed
     * in but does not take on the merged image)
     */
    public void drawUnit(GameUnit gameUnit) throws IOException {
        // read overlay image
        BufferedImage overlay = gameUnit.getUnitImage();

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(terrain.getTerrainImage().getWidth(), overlay.getWidth());
        int h = Math.max(terrain.getTerrainImage().getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g;
        g = combined.getGraphics();
        g.drawImage(terrain.getTerrainImage(), 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        // set unit
        this.gameUnit = gameUnit;

        // save as new image
        setIcon(new ImageIcon(combined));
    }

    /**
     * This method adds a white sheen over the terrainIcon and creates a
     * combined icon to display
     */
    public void glossCellForMove() throws IOException {
        // load source images
        BufferedImage overlay = ImageIO.read(new File(
                "semi-transparent-move.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(terrain.getTerrainImage().getWidth(), overlay.getWidth());
        int h = Math.max(terrain.getTerrainImage().getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g;
        g = combined.getGraphics();
        g.drawImage(terrain.getTerrainImage(), 0, 0, null);
        g.drawImage(overlay, 0, 0, null);

        // Save as new image
        setIcon(new ImageIcon(combined));
    }

    public int getRow() {
        return position.getRow();
    }

    public int getCol() {
        return position.getCol();
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}