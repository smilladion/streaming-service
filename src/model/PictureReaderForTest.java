package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PictureReaderForTest {
    public static void main (String avg[]) {
    BufferedImage photo = null;
    ArrayList<BufferedImage> picsHere = new ArrayList<>();

    try {
        File folder = new File("resources/pics/Film");
        File[] pics = folder.listFiles();
        String title;
        for (File f : pics) {
            if (f.isFile()) {
                photo = ImageIO.read(f);
                picsHere.add(photo);
                System.out.println("Cover for: " + f.getName());
            }
        }
    }
    catch(IOException e) {
    }
    int i = 0;
    System.out.println("TEST 1: ");

            /*int i = 0;
            ImageIcon image = new ImageIcon(picsHere.get(i));
            JLabel label = new JLabel("Picture 0", image, JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add( label, BorderLayout.CENTER );*/
    }
}

//TEST1
/*        File folder = new File("resources/pics/Film");
        File[] pics = folder.listFiles();
        for (File f: pics) {
            if (f.isFile()) {
                System.out.println("File " + f.getName());
            }
        }*/
//TEST2
/*
    public void PictureReaderForTest()  {
            ArrayList<Image> pictures = new ArrayList<>();  //Opretter array
            BufferedImage img = ImageIO.read(new File("resources/pics/Film/12 Angry Men")); //Finder specifik fil
            pictures.add(img); //Tilføjer specificerede fil
        }
    }
}
*/

