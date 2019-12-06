package view;

import javafx.scene.layout.BorderRepeat;
import model.Media;
import model.Streaming;
import model.User;
import view.WrapLayout;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class Display {

    private Streaming service = new Streaming(new User("Test"));
    private JFrame frame;
    private JPanel mediaPanel;
    private JPanel buttonPanel;
    private ArrayList<Media> results;
    private boolean isHome = true;


    public Display() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        mediaPanel = new JPanel(new WrapLayout(WrapLayout.CENTER, 20, 20)); // Sætter layout for mediaPanel til custom WrapLayout.
        buttonPanel = new JPanel();
        results = new ArrayList<>();

        mediaPanel.setBackground(new Color(31, 31, 31));
        buttonPanel.setSize(300, 100);
        buttonPanel.setBackground(Color.BLACK);

        frame.add(mediaPanel, BorderLayout.SOUTH);
        frame.add(buttonPanel, BorderLayout.NORTH);

        /*Opretter scrollbar. Bemærk at alt det, som skal kunne scrolles igennem
        (dvs. vores JPanel panel, som indeholder Media content) indsættes i scrollbaren som et argument den tager.
        Derved bliver panel så også tilføjet til frame idet at scrollbaren bliver tilføjet til frame:*/
        JScrollPane scrollBar = new JScrollPane(mediaPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.getVerticalScrollBar().setUnitIncrement(16); // Sætter scroll-hastigheden op.
        frame.add(scrollBar); // Tilføjer scrollbar til frame.

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true); // Viser det.

        // Fylder content-listen med data.
        service.fillMovies();
        service.fillSeries();
    }

    // Viser indholdet af contents-listen på skærmen.
    public void showAll() { //køres ved opstart
        showMedia(service.getContent());
    }

    // Viser resultaterne af søgningen.
    public void showResults(String txt) {
        for(Media media : service.getContent()) {
            if (media.getTitle().contains(txt)) {
                results.add(media);
            }
        }
        showMedia(results);
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

        JButton homepage = new JButton("Home");
        homepage.setForeground(Color.WHITE);
        homepage.setBackground(Color.BLACK);

        JTextField findText = new JTextField(20); // Virker kun med titler lige nu
        JButton search = new JButton("Search");

        buttonPanel.add(findText);
        buttonPanel.add(search);
        buttonPanel.add(user);
        buttonPanel.add(fav);
        buttonPanel.add(sort);
        buttonPanel.add(homepage);
        frame.setVisible(true);

        /* TODO Bugs: kan ikke gå fra search til favourites, og fra favourites til search fucker den også.
            Skal nok oprette et nyt tjek med tal, der repræsenterer siden, i stedet for en boolean.
         */
        findText.addActionListener(e-> {
            results.removeAll(results);
            mediaPanel.removeAll();
            showResults(findText.getText());
            isHome = false;
        });
        search.addActionListener(e-> {
            results.removeAll(results);
            mediaPanel.removeAll();
            showResults(findText.getText());
            isHome = false;
        });

        homepage.addActionListener(e -> {
            if (!isHome) {
                mediaPanel.removeAll();
                showAll();
                isHome = true;
            }
        });
        fav.addActionListener(e -> {
            if (isHome) {
                mediaPanel.removeAll();
                showFavourites();
                isHome = false;
            }
        });
    }

    public void showFavourites() {
        service.getPrimary().getFavourites().removeAll(service.getPrimary().getFavourites());
        service.getPrimary().addFavourite(service.getContent().get(1));
        service.getPrimary().addFavourite(service.getContent().get(2));
        showMedia(service.getPrimary().getFavourites());
    }

    // Viser alle film/serier fra den valgte liste i mediaPanel.
    public void showMedia(ArrayList<Media> media) {
        for (Media m : media) {
            JLabel image = new JLabel(new ImageIcon(m.getCover())); // Laver en JLabel indeholdende ImageIcon, med billedet.
            image.setText(m.getTitle()); // Sætter billedets tekst til dens titel
            image.setHorizontalTextPosition(SwingConstants.CENTER); // Gør at teksten befinder sig i midten
            image.setVerticalTextPosition(SwingConstants.BOTTOM); // ... og under billedet
            image.setForeground(Color.WHITE);
            mediaPanel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
        frame.setVisible(true);
    }
}
