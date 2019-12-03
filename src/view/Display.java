package view;

import model.Media;
import model.Streaming;
import model.User;
import model.WrapLayout;
import javax.swing.*;

public class Display {

    private Streaming service = new Streaming(new User("Test"));
    private JFrame frame;
    private JPanel panel;

    public Display() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(new WrapLayout());
        panel.setLayout(new WrapLayout()); // Sætter layout for panel til custom WrapLayout.

        /*Opretter scrollbar. Bemærk at alt det, som skal kunne scrolles igennem
        (dvs. vores JPanel panel, som indeholder Media content) indsættes i scrollbaren som et argument den tager.
        Derved bliver panel så også tilføjet til frame idet at scrollbaren bliver tilføjet til frame:*/
        JScrollPane scrollBar = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.getVerticalScrollBar().setUnitIncrement(16); // Sætter scroll-hastigheden op.
        frame.add(scrollBar); // Tilføjer scrollbar til frame.


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack(); // Viser det.
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
            panel.add(image); // Tilføjer billedet til vinduet fra konstruktoren.
        }
    }
}







