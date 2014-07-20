package units;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class GameUnit {
	protected Position position;
	protected BufferedImage unitImage;
	protected int stamina = 0;

	protected GameUnit(Position position) throws IOException {
		this.position = position;
		// unitImage = ImageIO.read(new File("transparent.png"));
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public BufferedImage getUnitImage() {
		return unitImage;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
}
