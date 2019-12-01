package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PictureReaderForTest {
    public static BufferedImage photo = null;
    public static Map<String, BufferedImage> picFolder = new HashMap<>(1000);

    public PictureReaderForTest(){
        photo = null;
        picFolder = new HashMap<>(1000);
    }



    public static void loadHash() throws IOException {
            File folder = new File("resources/pics/Film"); //TODO viser kun billeder+tekst for film atm, skal også vise for serier
            File[] pics = folder.listFiles();
            String picTitle;
            for (File f : pics) {
                if (f.isFile()) {
                    photo = ImageIO.read(f);
                    picTitle = f.getName();
                    picFolder.put(picTitle,photo);
                    System.out.println("Cover for: " + f.getName()); //Tester bare et udprint af listen, TODO skal fjernes fra den endelige kode
                }
            }
        }

    public static void showHash(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new WrapLayout()); //Sætter layout for panel til custom WrapLayout
        /*Opretter scrollbar. Bemærk at alt det, som skal kunne scrolles igennem
        (dvs. vores JPanel panel, som indeholder Media content) indsættes i scrollbaren som et argument den tager.
        Derved bliver panel så også tilføjet til frame idet at scrollbaren bliver tilføjet til frame:*/
        JScrollPane scrollBar=new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.getVerticalScrollBar().setUnitIncrement(16); //Sætter scroll-hastigheden op
        frame.add(scrollBar); //Tilføjer scrollbar til frame



        for(Map.Entry<String, BufferedImage> e : picFolder.entrySet()){ //tilføjer hvert billede til panel
            String key = e.getKey();
            BufferedImage value = e.getValue();
            JLabel image = new JLabel(new ImageIcon(value));
            image.setText(key);
            image.setHorizontalTextPosition(SwingConstants.CENTER);
            image.setVerticalTextPosition(SwingConstants.BOTTOM);
            panel.add(image);

            //String one = "12 Angry Men.jpg"; //en string
            //picFolder.get(one);//giver det ene billede tilhørende den String
            //JLabel image = new JLabel(key,new ImageIcon(value),SwingConstants.CENTER);
            //ovenstående opretter JLabel med tekst,image,tekstposition
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); //viser det.
        frame.setVisible(true);
    }


    public static void main (String avg[]) throws IOException {

    try {
        loadHash();
        showHash();
    }
    catch(IOException e) {
    }
    }
}



    //TEST FOR AT FÅ VIST BILLED FRA HASHMAP HERFRA:
        //TODO ryk det her over i view på et senere tidspunkt, så det køres defra.
        //public void forEachHash() //obs viser 100 billeder. USE WITH CAUTION!!!!!!
        /*JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout()); //kreerer flowlayout jframe

            for(Map.Entry<String, BufferedImage> e : picFolder.entrySet()){ //tilføjer hvert billede til JFrame
                String key = e.getKey();
                BufferedImage value = e.getValue();
                JLabel image = new JLabel(new ImageIcon(value));
                image.setText(key);
                image.setHorizontalTextPosition(SwingConstants.CENTER);
                image.setVerticalTextPosition(SwingConstants.BOTTOM);
                frame.getContentPane().add(image);

                //String one = "12 Angry Men.jpg"; //en string
                //picFolder.get(one);//giver det ene billede tilhørende den String
                //JLabel image = new JLabel(key,new ImageIcon(value),SwingConstants.CENTER);
                //ovenstående opretter JLabel med tekst,image,tekstposition, men teksten sidder ikke rigtigt.
            }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); //viser det.
        frame.setVisible(true);
        }
    }




            /*ImageIcon image = new ImageIcon("resources/pics/Film/12 Angry Men.jpg");
            JLabel label = new JLabel("Picture 0", image, JLabel.CENTER);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add( label, BorderLayout.CENTER );*/





