package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Mountains extends Terrain {

    public Mountains() throws IOException {
        terrainImage = ImageIO.read(new File("transparent.png"));
        defenseRating = 3;
        staminaCost = 3;
    }
}
