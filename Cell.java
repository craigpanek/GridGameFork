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

@SuppressWarnings("serial")
public class Cell extends JLabel {
	private boolean selected;
	private ImageIcon terrain;

	public Cell(ImageIcon terrain) {
		this.terrain = terrain;
		setIcon(terrain);
		setBorder(new LineBorder(new Color(15, 130, 20)));
		selected = false;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setTerrain(ImageIcon terrain) {
		this.terrain = terrain;
		setIcon(terrain);
	}

	public void drawUnit() throws IOException {
		// load source images
		BufferedImage image = ImageIO.read(new File("terrain-plains-64.png"));
		BufferedImage overlay = ImageIO.read(new File("unit-laborer.png"));

		// create the new image, canvas size is the max. of both image sizes
		int w = Math.max(image.getWidth(), overlay.getWidth());
		int h = Math.max(image.getHeight(), overlay.getHeight());
		BufferedImage combined = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, 0, 0, null);

		// Save as new image
		setIcon(new ImageIcon(combined));
	}
}