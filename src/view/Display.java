package view;

import javafx.scene.layout.BorderRepeat;
import model.Media;
import model.Streaming;
import model.User;
import model.WrapLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Display {

    private Streaming service = new Streaming(new User("Test"));
    private JFrame frame;
    private JPanel mediaPanel;
    private JPanel buttonPanel;
    private JPanel favPanel; //TODO TEST

    public Display() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        mediaPanel = new JPanel(new WrapLayout(WrapLayout.CENTER, 20, 20)); // Sætter layout for mediapanel til custom WrapLayout.
        favPanel = new JPanel(new WrapLayout(WrapLayout.CENTER, 20, 20)); // Sætter layout for favpanel til custom WrapLayout.//TODO TEST
        buttonPanel = new JPanel();

        mediaPanel.setBackground(new Color(31, 31, 31));
        favPanel.setBackground(new Color(31, 31, 31)); //favPanel //TODO TEST
        buttonPanel.setSize(300, 100);
        buttonPanel.setBackground(Color.BLACK);

        frame.add(mediaPanel, BorderLayout.SOUTH);
        frame.add(favPanel, BorderLayout.SOUTH); //favPanel //TODO TEST
        frame.add(buttonPanel, BorderLayout.NORTH);

        /*Opretter scrollbar. Bemærk at alt det, som skal kunne scrolles igennem
        (dvs. vores JPanel panel, som indeholder Media content) indsættes i scrollbaren som et argument den tager.
        Derved bliver panel så også tilføjet til frame idet at scrollbaren bliver tilføjet til frame:*/
        JScrollPane scrollBar = new JScrollPane(mediaPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //JScrollPane scrollBar1 = new JScrollPane(favPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//TODO TEST
        scrollBar.getVerticalScrollBar().setUnitIncrement(16); // Sætter scroll-hastigheden op.
        //scrollBar1.getVerticalScrollBar().setUnitIncrement(16); // Sætter scroll-hastigheden op.//TODO test
        frame.add(scrollBar); // Tilføjer scrollbar til frame.
        //frame.add(scrollBar1); // Tilføjer scrollbar til frame.//TODO TEST

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Viser det.
        frame.setSize(800, 600);
        frame.setVisible(true);

        // Fylder content-listen med data.
        service.fillMovies();
        service.fillSeries();
    }

    // Viser indholdet af contents-listen på skærmen.
    public void showMedia() {
        for (Media media : service.getContent()) {
            JLabel image = new JLabel(new ImageIcon(media.getCover())); // Laver en JLabel indeholdende ImageIcon, med billedet.
            image.setText(media.getTitle()); // Sætter billedets tekst til dens titel
            image.setHorizontalTextPosition(SwingConstants.CENTER); // Gør at teksten befinder sig i midten
            image.setVerticalTextPosition(SwingConstants.BOTTOM); // ... og under billedet
            image.setForeground(Color.WHITE);
            mediaPanel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
        mediaPanel.setVisible(true);
        frame.setVisible(true);
    }

    // TODO Måske typen af medie (film/serie) og genre skal være to forskellige dropdowns? (Så man kan sortere medie OG genre samtidigt)
    public void showButtons() {
        JButton user = new JButton("User");
        user.setForeground(Color.WHITE);
        user.setBackground(Color.BLACK);

        JButton fav = new JButton("Favourites");
        fav.setForeground(Color.WHITE);
        fav.setBackground(Color.BLACK);

        JButton sort = new JButton("Sort by");
        sort.setForeground(Color.WHITE);
        sort.setBackground(Color.BLACK);

        JButton search = new JButton("Search");
        search.setForeground(Color.WHITE);
        search.setBackground(Color.BLACK);

        JButton homepage = new JButton("Homepage");
        homepage.setForeground(Color.WHITE);
        homepage.setBackground(Color.BLACK);

        buttonPanel.add(user);
        buttonPanel.add(fav);
        buttonPanel.add(sort);
        buttonPanel.add(search);
        buttonPanel.add(homepage);
        frame.setVisible(true);

        homepage.addActionListener(e-> mediaPanel.setVisible(true));
        fav.addActionListener(e -> {
            mediaPanel.setVisible(false);
            favPanel.setVisible(true);
        });                  //TODO hvor skal vores actionlisteners være??
    }

    public void showFavourites(){
        service.getPrimary().addFavourite(service.getContent().get(1));
        for (Media media : service.getPrimary().getFavourites()) {
            JLabel image = new JLabel(new ImageIcon(media.getCover())); // Laver en JLabel indeholdende ImageIcon, med billedet.
            image.setText(media.getTitle()); // Sætter billedets tekst til dens titel
            image.setHorizontalTextPosition(SwingConstants.CENTER); // Gør at teksten befinder sig i midten
            image.setVerticalTextPosition(SwingConstants.BOTTOM); // ... og under billedet
            image.setForeground(Color.WHITE);
            favPanel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
    mediaPanel.setVisible(false);
        favPanel.setVisible(true);
    }
}







