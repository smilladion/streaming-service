package view;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Display {

    private Streaming service = new Streaming(new User("Bob"), new User("Jean"));
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


        ActionListener listener = e -> {
            results.clear();
            clean(mediaPanel);
            page = PageType.SEARCH;
            try {
                showResults(findText.getText());
            } catch (MediaNotFoundException exception) {
                JLabel msg = new JLabel(exception.getMessage());
                msg.setForeground(Color.WHITE);
                mediaPanel.add(msg);
            }
        };

        // Tilføjer den oprettede listener defineret oven over, så tekstfeltet og search-knappen gør det samme
        findText.addActionListener(listener);
        search.addActionListener(listener);

        homepage.addActionListener(e -> {
            if (page != PageType.HOME) {
                clean(mediaPanel);
                showAll();
                page = PageType.HOME;
            }
        });

        user.addActionListener(e -> {
            if (page != PageType.USER) {
                clean(mediaPanel);
                page = PageType.USER;

                // Tilføj knapper for hver bruger i listen, samt deres Listener
                for (User u : service.getUsers()) {
                    JButton button = new JButton(u.getName());
                    mediaPanel.add(button);

                    button.addActionListener(event -> {
                        for (Component comp : mediaPanel.getComponents()) {
                            if (comp instanceof JButton) {
                                comp.setForeground(Color.WHITE); // Sætter alle knapper på panelet til en specifik farve (sort)
                                comp.setBackground(Color.BLACK);
                            }
                        }

                        service.setPrimary(u);
                        button.setForeground(Color.BLACK); // Sætter den primære bruger til en specifik farve (hvid)
                        button.setBackground(Color.WHITE);
                        mediaPanel.revalidate();
                        mediaPanel.repaint();
                    });

                    // Når man går ind på User fanen, så sæt farverne alt efter hvilken bruger der er primær
                    if (button.getText().equals(service.getPrimary().getName())) {
                        button.setForeground(Color.BLACK);
                        button.setBackground(Color.WHITE);
                    } else {
                        button.setForeground(Color.WHITE);
                        button.setBackground(Color.BLACK);
                    }
                }

                // TODO Kode til at tilføje add/remove user knapper
                /* JButton addUser = new JButton("ADD USER");
                JButton removeUser = new JButton("REMOVE USER");
                mediaPanel.add(addUser);
                mediaPanel.add(removeUser);

                addUser.addActionListener(event -> {
                    String name = JOptionPane.showInputDialog(frame, "Please type in the name of the new user:");
                    service.getUsers().add(new User(name));
                }); */

            }
        });

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

        // Sorterer Media på både Film/Series og Genre
        ActionListener listenerSort = e -> {

            ArrayList<Media> selectedMedia = new ArrayList<>();
            String selectedType = mediaType.getSelectedItem().toString();
            String selectedGenre = genreType.getSelectedItem().toString();

            if (selectedType.equals("All") && !selectedGenre.equals("Genre")) {
                for (Media media : service.getContent()) {
                    if (media.getGenre().contains(selectedGenre)) {
                        selectedMedia.add(media);
                    }
                }
            } else if (selectedType.equals("Series") && !selectedGenre.equals("Genre")) {
                for (Media media : service.getContent()) {
                    if (media.getGenre().contains(selectedGenre) && media instanceof Series) {
                        selectedMedia.add(media);
                    }
                }
            } else if (selectedType.equals("Films") && !selectedGenre.equals("Genre")) {
                for (Media media : service.getContent()) {
                    if (media.getGenre().contains(selectedGenre) && media instanceof Film) {
                        selectedMedia.add(media);
                    }
                }
            } else if (selectedType.equals("Series")) { // Tjekkede oven over om genren != "Genre", så behøver ikke tjekke det omvendte her...
                for (Media media : service.getContent()) { //... når koden hertil, så er selectedGenre altid = "Genre"
                    if (media instanceof Series) {
                        selectedMedia.add(media);
                    }
                }
            } else if (selectedType.equals("Films")) {
                for (Media media : service.getContent()) {
                    if (media instanceof Film) {
                        selectedMedia.add(media);
                    }
                }
            }

            if (selectedType.equals("All") & selectedGenre.equals("Genre")) {
                clean(mediaPanel);
                showAll();
                page = PageType.HOME;
            } else {
                clean(mediaPanel);
                showMedia(selectedMedia);
                page = PageType.SORT;
            }
        };

        genreType.addActionListener(listenerSort);
        mediaType.addActionListener(listenerSort);
    }


    public void showFavourites() {
        showMedia(service.getPrimary().getFavourites());

        if (service.getPrimary().getFavourites().isEmpty()) {
            throw new NoFavsException();
        }
    }

    // Viser alle film/serier fra den valgte liste i mediaPanel, og tilføjer en mouselistener så kan man trykke på billede og se info.
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
                layout(gbc, 1, 0, 1);
                gbc.insets = new Insets(0,10,0,0); //ændrer afstanden den holder til de andre komponenter
                gbc.anchor = GridBagConstraints.WEST; //gør at teksten holder sig i venstre side
                boxPane.add(title,gbc); //tilføjer den til layoutet

                JLabel genre = new JLabel("Genre: "+m.getGenre());
                genre.setForeground(Color.WHITE);
                layout(gbc, 1, 1, 1);
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(genre,gbc);

                JLabel year = new JLabel("Year: "+m.getYear());
                year.setForeground(Color.WHITE);
                layout(gbc, 1, 2, 1);
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(year,gbc);

                String s1 = Float.toString(m.getRating());
                JLabel rating = new JLabel("Rating: "+s1);
                rating.setForeground(Color.WHITE);
                layout(gbc, 1, 3, 1);
                gbc.insets = new Insets(0,10,0,0);
                boxPane.add(rating,gbc);

                //delen nedenunder tilføjer billedet
                layout(gbc, 0, 0, 14);
                gbc.fill = GridBagConstraints.BOTH;
                boxPane.add(image, gbc);

                // Tilføjer valg af sæson/episoder hos serierne
                if(m instanceof Series) {
                    ArrayList<Integer> seasonList = new ArrayList<>();

                    for (Map.Entry<Integer, Integer> season : ((Series) m).getSeasons().entrySet()) {
                        seasonList.add(season.getKey());
                    }

                    JLabel season = new JLabel("Season:");
                    season.setForeground(Color.WHITE);
                    layout(gbc, 1, 4, 1);
                    gbc.insets = new Insets(15,10,0,0);
                    boxPane.add(season, gbc);

                    JComboBox<Integer> seasonBox = new JComboBox<>(seasonList.toArray(new Integer[0]));
                    seasonBox.setBackground(Color.BLACK);
                    seasonBox.setForeground(Color.WHITE);
                    layout(gbc, 1, 5, 1);
                    gbc.insets = new Insets(0,10,5,0);
                    boxPane.add(seasonBox, gbc);

                    JComboBox<Integer> episodeBox = new JComboBox<>();
                    episodeBox.setBackground(Color.BLACK);
                    episodeBox.setForeground(Color.WHITE);
                    layout(gbc,1,7,1);
                    gbc.insets = new Insets(0,10,5,0);

                    seasonBox.addActionListener(event -> {
                        episodeBox.removeAllItems();
                        for (Map.Entry<Integer, Integer> s : ((Series) m).getSeasons().entrySet()) {
                            if (seasonBox.getSelectedItem().equals(s.getKey())) {

                                JLabel episode = new JLabel("Episode:");
                                episode.setForeground(Color.WHITE);
                                layout(gbc,1,6,1);
                                gbc.insets = new Insets(15,10,0,0);
                                boxPane.add(episode,gbc);

                                for (int i = 1; i <= s.getValue(); i++) {

                                    episodeBox.addItem(i);

                                    // TODO Kode til at vise episoderne. Skal gøres inde i dette for loop.
                                    // TODO Det der står her nu virker, hvis man indsætter System.out.println(i).
                                    // TODO Kan dog ikke få noget Swing relateret til at vise på skærmen.
                                }
                                boxPane.add(episodeBox,gbc);

                            }

                            mediaPanel.revalidate();
                            mediaPanel.repaint();
                        }
                    });
                }

                JButton play = new JButton("PLAY");
                layout(gbc, 1, 8, 1);
                gbc.insets = new Insets(30,10,0,0);
                boxPane.add(play,gbc);

                if (page != PageType.FAVS) {
                    JButton addToFav = new JButton("ADD TO FAVOURITES");
                    layout(gbc, 1, 9, 1);
                    gbc.insets = new Insets(15,10,0,0);
                    boxPane.add(addToFav,gbc);

                    addToFav.addActionListener(event -> {
                        try {
                            service.getPrimary().addFavourite(m);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(frame,ex.getMessage());
                        }
                    });
                } else {
                    JButton removeFav = new JButton("REMOVE FROM FAVOURITES");
                    layout(gbc, 1, 10, 1);
                    gbc.insets = new Insets(20,10,0,0);
                    boxPane.add(removeFav,gbc);

                    removeFav.addActionListener(event -> {
                        service.getPrimary().removeFavourite(m);
                    });
                }

                mediaPanel.add(boxPane,BorderLayout.CENTER);

                play.addActionListener(event-> {
                        try{
                            playMedia();
                        }catch(java.net.URISyntaxException exc) {
                            System.err.println(exc.getMessage());
                        }
                });

                page = PageType.INFO;
            }});
            mediaPanel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
        frame.setVisible(true);
    }

    public void playMedia() throws java.net.URISyntaxException{ //åbner browser hvor den afspiller en youtubevideo.
        final URI uri = new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(uri);
            } catch(IOException e){
                System.err.println(e.getMessage());
            }
        }
    }

    // Fjerner alt fra panelet.
    public void clean(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        panel.revalidate();
    }

    /* Bruges i showMedia() til layout. Indeholder ikke .insets(), fordi den selv har fire parametre som skiftes ud ofte, og
    heller ikke .add() til panelet, da denne skal kaldes efter .insets(). */
    public void layout(GridBagConstraints gbc, int x, int y, int height) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridheight = height;
    }
}
