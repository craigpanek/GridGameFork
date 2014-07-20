package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class River extends Terrain {

    public River() throws IOException {
        terrainImage = ImageIO.read(new File("transparent.png"));
        defenseRating = 2;
        staminaCost = 2;
    }
}