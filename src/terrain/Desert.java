package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Desert extends Terrain {

    public Desert() throws IOException {
        terrainImage = ImageIO.read(new File("terrain-desert-64.png"));
        defenseRating = 1;
        staminaCost = 1;
    }
}
