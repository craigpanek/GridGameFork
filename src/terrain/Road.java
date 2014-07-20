package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Road extends Terrain {

    public Road() throws IOException {
        terrainImage = ImageIO.read(new File("transparent.png"));
        defenseRating = 0;
        staminaCost = 1;
    }
}
