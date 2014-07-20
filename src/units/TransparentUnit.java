package units;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TransparentUnit extends GameUnit {

	public TransparentUnit(Position position) throws IOException {
		super(position);
		this.unitImage = ImageIO.read(new File("transparent.png"));
	}
}
