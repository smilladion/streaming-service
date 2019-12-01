package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PictureReaderForTest {
    public static void main (String avg[]) {
    BufferedImage photo = null;
    Map<String, BufferedImage> picFolder = new HashMap<>(1000);


    try {
        File folder = new File("resources/pics/Film");
        File[] pics = folder.listFiles();
        String picTitle;
        for (File f : pics) {
            if (f.isFile()) {
                photo = ImageIO.read(f);
                picTitle = f.getName();
                picFolder.put(picTitle,photo);
                System.out.println("Cover for: " + f.getName()); //Tester bare et udprint af listen, skal self ikke med i den endelige kode
            }
        }
    }
    catch(IOException e) {
    }
    //Tester for at bekræfte om en titel er blevet lagret i hashmappet - den returner true, så det er den :)
    System.out.println("TEST 1: "+picFolder.containsKey("12 Angry Men.jpg"));

    //TEST FOR AT FÅ VIST BILLED FRA HASHMAP HERFRA:
        //public void forEachHash() //obs viser 100 billeder. USE WITH CAUTION!!!!!!
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout()); //kreerer flowlayout jframe

            for(Map.Entry<String, BufferedImage> e : picFolder.entrySet()){ //tilføjer hvert billede til JFrame
                String key = e.getKey();
                BufferedImage value = e.getValue();
                JLabel image = new JLabel(new ImageIcon(value));
                image.setText(key);
                image.setHorizontalTextPosition(SwingConstants.CENTER);
                image.setVerticalTextPosition(SwingConstants.BOTTOM);
                frame.getContentPane().add(image);
//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //String one = "12 Angry Men.jpg"; //en string
                //picFolder.get(one);//giver det ene billede tilhørende den String
                //JLabel image = new JLabel(key,new ImageIcon(value),SwingConstants.CENTER);
                //ovenstående opretter JLabel med tekst,image,tekstposition, men teksten sidder ikke rigtigt.
            }
        frame.pack(); //viser det.
        frame.setVisible(true);
        }
    }




            /*ImageIcon image = new ImageIcon("resources/pics/Film/12 Angry Men.jpg");
            JLabel label = new JLabel("Picture 0", image, JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add( label, BorderLayout.CENTER );*/



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

