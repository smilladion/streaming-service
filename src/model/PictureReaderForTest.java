package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class PictureReaderForTest {
    public static void main (String avg[]) {
    }

    public PictureReaderForTest() throws IOException {
        ArrayList<Image> pictures = new ArrayList<>();  //Opretter array
        BufferedImage img = ImageIO.read(new File("resources/pics/Film/12 Angry Men")); //Finder specifik fil
        pictures.add(img); //Tilføjer specificerede fil
    }
}