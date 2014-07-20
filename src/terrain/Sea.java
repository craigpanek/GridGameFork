package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sea extends Terrain  {

    public Sea() throws IOException {
        terrainImage = ImageIO.read(new File("terrain-sea-64.png"));
        defenseRating = 0;
        staminaCost = 0;
    }
}
