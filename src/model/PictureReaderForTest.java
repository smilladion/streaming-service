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
        /*public void forEachHash() //iterater-metode: obs viser 100 billeder. USE WITH CAUTION!!!!!!
            for(Map.Entry<String, BufferedImage> e : picFolder.entrySet()){
                String key = e.getKey();
                BufferedImage value = e.getValue();*/
        //frame.setDefaultCloseO //viser den givne titel for værdi i String one:
                String one = "12 Angry Men.jpg"; //en string
                BufferedImage value = picFolder.get(one);//giver det ene billede tilhørende den String
                JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(value)));
                frame.pack();
                frame.setVisible(true);
            }
        }
    //}


