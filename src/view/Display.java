package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

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
    public void showResults(String txt) throws MediaNotFoundException {
        for (Media media : service.getContent()) {
            if (media.getTitle().toLowerCase().contains(txt.toLowerCase())) {
                results.add(media);
            }
        }

        showMedia(results);

        if (results.isEmpty()) {
            throw new MediaNotFoundException();
        }
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

        // TODO Bug: exception virker kun ved første search af en eller anden grund, skal fixes
        ActionListener listener = e -> {
            results.clear();
            clean(mediaPanel);
            page = PageType.SEARCH;
            try {
                showResults(findText.getText());
            } catch (MediaNotFoundException exception) {
                JLabel msg = new JLabel("Yeet boy: " + exception.getMessage());
                msg.setForeground(Color.WHITE);
                mediaPanel.add(msg);
            }
        };

        findText.addActionListener(listener);
        search.addActionListener(listener);

        homepage.addActionListener(e -> {
            if (page != PageType.HOME) {
                clean(mediaPanel);
                showAll();
                page = PageType.HOME;
            }
        });

        // TODO Hvis man fjerner den sidste film i listen ser man ikke exception før man har trykket på home page
        fav.addActionListener(e -> {
            if (page != PageType.FAVS) {
                clean(mediaPanel);
                page = PageType.FAVS;
                try {
                    showFavourites();
                } catch (NoFavsException ex) {
                    JLabel msg = new JLabel(ex.getMessage());
                    msg.setForeground(Color.WHITE);
                    mediaPanel.add(msg);
                }
            }
        });

        // Tjekker hvilken dropdown der blev valgt, og herefter viser de medier der er af typen Film eller Series.
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

    public void showFavourites() {
        showMedia(service.getPrimary().getFavourites());

        if (service.getPrimary().getFavourites().isEmpty()) {
            throw new NoFavsException();
        }
    }

    // Viser alle film/serier fra den valgte liste i mediaPanel.
    public void showMedia(ArrayList<Media> media) {
        for (Media m : media) {
            JLabel image = new JLabel(new ImageIcon(m.getCover())); // Laver en JLabel indeholdende ImageIcon, med billedet.

            image.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clean(mediaPanel);
                JPanel boxPane = new JPanel(new GridBagLayout());
                boxPane.setBackground(new Color(31, 31, 31));
                GridBagConstraints gbc = new GridBagConstraints(); //gør så man kan ændre gridbags layout

                JLabel title = new JLabel("Title: "+m.getTitle());
                title.setForeground(Color.WHITE);
                gbc.gridx = 1; //sæter dens x-position
                gbc.gridy = 0; //sætter dens y-position
                gbc.gridheight = 1; //sætter dens højde
                gbc.insets = new Insets(0,10,0,0); //ændrer afstanden den holder til de andre komponenter
                gbc.anchor = GridBagConstraints.WEST; //gør at teksten holder sig i venstre side
                boxPane.add(title,gbc); //tilføjer den til layoutet

                JLabel genre = new JLabel("Genre: "+m.getGenre());
                genre.setForeground(Color.WHITE);
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(genre,gbc);

                JLabel year = new JLabel("Year: "+m.getYear());
                year.setForeground(Color.WHITE);
                gbc.gridx = 1;
                gbc.gridy=2;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(year,gbc);

                String s1 = Float.toString(m.getRating());
                JLabel rating = new JLabel("Rating: "+s1);
                rating.setForeground(Color.WHITE);
                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(rating,gbc);

                //delen nedenunder tilføjer billedet
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridheight = 10;
                gbc.fill = GridBagConstraints.BOTH;
                boxPane.add(image,gbc);

                String s3 = new String();
                if(m instanceof Series) {
                    s3 = ((Series) m).displaySeasons();
                }

                JLabel seasons = new JLabel(s3);
                seasons.setForeground(Color.WHITE);
                gbc.gridx = 1;
                gbc.gridy = 5;
                gbc.gridheight = 1;
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(seasons,gbc);

                JButton play = new JButton("PLAY");
                gbc.gridx = 1;
                gbc.gridy = 7;
                gbc.gridheight = 1;
                gbc.insets = new Insets(30,10,0,0);
                boxPane.add(play,gbc);

                if (page != PageType.FAVS) {
                    JButton addToFav = new JButton("ADD TO FAVOURITES");
                    gbc.gridx = 1;
                    gbc.gridy = 8;
                    gbc.gridheight = 1;
                    gbc.insets = new Insets(20,10,0,0);
                    boxPane.add(addToFav,gbc);

                    addToFav.addActionListener(event -> {
                        try {
                            service.getPrimary().addFavourite(m);
                        } catch (Exception ex){
                            JOptionPane.showMessageDialog(frame,ex.getMessage());
                        }
                    });
                } else {
                    JButton removeFav = new JButton("REMOVE FROM FAVOURITES");
                    gbc.gridx = 1;
                    gbc.gridy = 8;
                    gbc.gridheight = 1;
                    gbc.insets = new Insets(20,10,0,0);
                    boxPane.add(removeFav,gbc);

                    removeFav.addActionListener(event -> {
                        service.getPrimary().removeFavourite(m);
                    });
                }

                mediaPanel.add(boxPane,BorderLayout.CENTER);

                play.addActionListener(event-> {}); //Indsæt noget som agerer playfunktion her

                page = PageType.INFO;
                mediaPanel.revalidate(); //opdaterer siden
                mediaPanel.repaint(); //opdaterer siden
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
