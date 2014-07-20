package terrain;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Forest extends Terrain{

    public Forest() throws IOException {
        terrainImage = ImageIO.read(new File("woods-64.png"));
        defenseRating = 2;
        staminaCost = 2;
    }
}
