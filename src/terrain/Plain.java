package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Plain extends Terrain {

    public Plain() throws IOException {
        terrainImage = ImageIO.read(new File("terrain-plains-64.png"));
        defenseRating = 1;
        staminaCost = 1;
    }
}
