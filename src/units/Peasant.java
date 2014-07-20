package units;

/**
 * Copyright 2014 Peter "Felix" Nguyen, Craig Panek
 */
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Peasant extends GameUnit implements Movable {

	private final int STAMINA = 3;
	
    public Peasant(Position position) throws IOException {
        super(position);
        unitImage = ImageIO.read(new File("peasant-64.png"));
        // stamina is Protected in the parent class
        stamina = STAMINA;
    }

    @Override
    public void moveNorth() {
        if (stamina != 0) {
            stamina--;
        }
    }

    @Override
    public void moveSouth() {
        if (stamina != 0) {
            stamina--;
        }
    }

    @Override
    public void moveEast() {
        if (stamina != 0) {
            stamina--;
        }
    }

    @Override
    public void moveWest() {
        if (stamina != 0) {
            stamina--;
        }
    }
}
