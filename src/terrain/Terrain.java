package terrain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Terrain {
    protected int defenseRating;
    protected BufferedImage terrainImage;
    /* Defense ratings: 0 -> no damage reduction
     *                  1 -> 10% less incoming damage
     *                  2 -> 20% less incoming damage
     *                  3 -> 30% less incoming damage
     *                  4 -> 40% less incoming damage
     *                  5 -> 50% less incoming damage
     */
    protected int staminaCost;
    /* Stamina cost: 0 -> can't be traversed on foot
     *               1 -> costs 1 stamina
     *               2 -> costs 2 stamina
     *               N -> etcetera
     */

    public Terrain() throws IOException {
        terrainImage = ImageIO.read(new File("transparent.png"));
        defenseRating = 0;
        staminaCost = 0;
    }

    public int getDefenseRating() {
        return defenseRating;
    }

    public int getStaminaCost() {
        return staminaCost;
    }

    public BufferedImage getTerrainImage() {
        return terrainImage;
    }

    // Set Terrain with ImageIcon (write another method for buffered image if required)
    public void setTerrainImage(ImageIcon imageIcon) {
        terrainImage = new BufferedImage(
                imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = terrainImage.createGraphics();

        // paint the Icon to the BufferedImage.
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();
    }
}
