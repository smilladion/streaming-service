package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Display {

    private Streaming service = new Streaming(new User("Test"));
    private JFrame frame;
    private JPanel mediaPanel;
    private JPanel buttonPanel;
    private ArrayList<Media> results;
    private PageType page = PageType.HOME;

    public Display() {
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        mediaPanel = new JPanel(new WrapLayout(WrapLayout.CENTER, 20, 20)); // Sætter layout for mediaPanel til custom WrapLayout.
        buttonPanel = new JPanel();
        results = new ArrayList<>();

        mediaPanel.setBackground(new Color(31, 31, 31));
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
        frame.setSize(1000, 800);
        frame.setVisible(true); // Viser det.

        // Fylder content-listen med data.
        service.fillMovies();
        service.fillSeries();

        // Arrangerer contents alfabetisk (så film/serier er blandede)
        service.getContent().sort(Comparator.comparing(Media::getTitle));
    }

    // Viser indholdet af contents-listen på skærmen.
    public void showAll() { //køres ved opstart
        showMedia(service.getContent());
    }

    // Viser resultaterne af søgningen.
    public void showResults(String txt) {
        for (Media media : service.getContent()) {
            if (media.getTitle().toLowerCase().contains(txt.toLowerCase())) {
                results.add(media);
            }
        }
        showMedia(results);
    }

    public void showButtons() {
        JButton user = new JButton("User");
        user.setForeground(Color.WHITE);
        user.setBackground(Color.BLACK);

        JButton fav = new JButton("Favourites");
        fav.setForeground(Color.WHITE);
        fav.setBackground(Color.BLACK);

        JButton homepage = new JButton("Home");
        homepage.setForeground(Color.WHITE);
        homepage.setBackground(Color.BLACK);

        JTextField findText = new JTextField(20); // Virker kun med titler
        JButton search = new JButton("Search");
        search.setForeground(Color.WHITE);
        search.setBackground(Color.BLACK);

        String[] mType = {"All", "Films", "Series"};
        JComboBox<String> mediaType = new JComboBox<>(mType);
        mediaType.setForeground(Color.WHITE);
        mediaType.setBackground(Color.BLACK);

        // TODO Lav evt dette om så der automatisk indlæses alle genrerne fra dataen i alfabetisk rækkefølge (lige nu er det gjort manuelt)
        String[] genre = {"Genre", "Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy",
                "Film-Noir", "History", "Horror", "Music", "Musical", "Mystery", "Romance", "Sci-fi", "Sport", "Talk-show", "Thriller", "War", "Western"};
        JComboBox<String> genreType = new JComboBox<>(genre);
        genreType.setForeground(Color.WHITE);
        genreType.setBackground(Color.BLACK);

        buttonPanel.add(homepage);
        buttonPanel.add(user);
        buttonPanel.add(fav);
        buttonPanel.add(mediaType);
        buttonPanel.add(genreType);
        buttonPanel.add(findText);
        buttonPanel.add(search);
        frame.setVisible(true);

        // TODO Tilføj en exception til search, som vises på skærmen hvis der ikke kunne findes noget medie med det navn
        findText.addActionListener(e -> {
            results.removeAll(results);
            clean(mediaPanel);
            showResults(findText.getText());
            page = PageType.SEARCH;
        });
        search.addActionListener(e -> {
            results.removeAll(results);
            clean(mediaPanel);
            showResults(findText.getText());
            page = PageType.SEARCH;
        });

        homepage.addActionListener(e -> {
            if (page != PageType.HOME) {
                clean(mediaPanel);
                showAll();
                page = PageType.HOME;
            }
        });
        fav.addActionListener(e -> {
            if (page != PageType.FAVS) {
                clean(mediaPanel);
                showFavourites();
                page = PageType.FAVS;
            }
        });

        /*
        IntelliJ formaterede et "if, else if, else if..." statement om til dette i stedet.
        Tjekker hvilken dropdown der blev valgt, og herefter viser de medier der er af typen Film eller Series.
        // TODO Ser ud til der skal være en exception til hvis .toString() fejler - nok bare i terminalen.
        */
        mediaType.addActionListener(e -> {
            ArrayList<Media> mediaSelect = new ArrayList<>();

            switch (mediaType.getSelectedItem().toString()) {
                case "All":
                    clean(mediaPanel);
                    showAll();
                    page = PageType.HOME;
                    break;
                case "Films":
                    for (Media media : service.getContent()) {
                        if (media instanceof Film) {
                            mediaSelect.add(media);
                        }
                    }
                    clean(mediaPanel);
                    showMedia(mediaSelect);
                    page = PageType.MEDIA;
                    break;
                case "Series":
                    for (Media media : service.getContent()) {
                        if (media instanceof Series) {
                            mediaSelect.add(media);
                        }
                    }
                    clean(mediaPanel);
                    showMedia(mediaSelect);
                    page = PageType.MEDIA;
                    break;
            }
        });

        // Tilføjer mediet til en liste hvis dens String med genrer indeholder den valgte dropdown (altså en specifik genre). Viser listen på skærmen.
        // TODO Lige nu kan man ikke vælge flere genre, eller vælge en type + en genre.
        // TODO Desuden skifter dropdown ikke tilbage til at stå på "All" eller "Genre" hvis man går væk fra sorteringen.
        genreType.addActionListener(e -> {
            ArrayList<Media> genreSelect = new ArrayList<>();

            if (genreType.getSelectedItem().toString().equals("Genre")) {
                clean(mediaPanel);
                showAll();
                page = PageType.HOME;
            } else {
                for (Media media : service.getContent()) {
                    if (media.getGenre().contains(genreType.getSelectedItem().toString())) {
                        genreSelect.add(media);
                    }
                }

                clean(mediaPanel);
                showMedia(genreSelect);
                page = PageType.GENRE;
            }
        });
    }

    // TODO Skal integreres med User, således der kan tilføjes/fjernes til favoritterne
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
            image.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                clean(mediaPanel);
                JPanel boxPane = new JPanel(new GridLayout(0, 1));
                boxPane.setBackground(new Color(31, 31, 31));
                JLabel title = new JLabel("Title: "+m.getTitle());
                title.setForeground(Color.WHITE);
                JLabel genre = new JLabel("Genre: "+m.getGenre());
                genre.setForeground(Color.WHITE);
                JLabel year = new JLabel("Year: "+m.getYear());
                year.setForeground(Color.WHITE);
                String s1 = Float.toString(m.getRating());
                JLabel rating = new JLabel("Rating: "+s1);
                rating.setForeground(Color.WHITE);
                String s3 = new String();
                if(m instanceof Series){
                    s3 = ((Series) m).displaySeasons(); }
                JLabel seasons = new JLabel(s3);
                seasons.setForeground(Color.WHITE);
                JButton play = new JButton("PLAY");
                JButton addToFav = new JButton("ADD TO FAVOURITES");

                boxPane.add(title);
                boxPane.add(year);
                boxPane.add(genre);
                boxPane.add(rating);
                boxPane.add(seasons);
                boxPane.add(play);
                boxPane.add(addToFav);
                mediaPanel.add(boxPane,BorderLayout.CENTER);

                addToFav.addActionListener(event->
                {service.getPrimary().addFavourite(m);    }); //Virker ikke?
                play.addActionListener(event-> {}); //Indsæt noget som agerer playfunktion her

                page = PageType.INFO;
            }});
            mediaPanel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
        frame.setVisible(true);
    }

    // Fjerner alt fra panelet.
    public void clean(JPanel panel) {
        panel.removeAll();
        panel.repaint();
    }
}
