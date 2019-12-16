package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Streaming {

    private final ArrayList<Media> content; // Indeholder alle film og serier vi har fået fra tekstfilerne
    private final ArrayList<User> users; // Indeholder alle de eksisterende brugere
    private User primary; // Den nuværende bruger som er valgt/logget ind

    public Streaming(User primary, User secondary) {
        this.primary = primary;
        users = new ArrayList<>();
        users.add(primary);
        users.add(secondary); // Secondary er der for at kunne skifte mellem to brugere så man kan se det virker
        content = new ArrayList<>();
    }

    public ArrayList<Media> getContent() {
        return content;
    }

    public User getPrimary() {
        return primary;
    }

    public void setPrimary(User user) {
        primary = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    // Fylder content med data, køres som det første når et Streaming-objekt oprettes (sammen med fillSeries()).
    public void fillMovies() {
        try {
            Scanner scanner = new Scanner(new File("resources/movies_text.txt")) // Opretter ny scanner til data
                    .useLocale(Locale.forLanguageTag("en-DK")) // Ændrer sproget som aflæses, således at kommaer kan konverteres til punktummer (ift. ratings)
                    .useDelimiter("; |;"); // Afgrænser hvilke symboler som deler informationen op: enten "; " eller ";" (fordi datafilen er halvdårlig og ikke konsekvent med mellemrum)

            File folder = new File("resources/pics/Film");
            File[] pics = folder.listFiles(); // Array med billedfiler til cover

            // Looper gennem hver linje i tekstfilen og sætter de individuelle data til deres specifikke felt
            while (scanner.hasNext()) {
                String title = scanner.next().trim(); // Trim-funktionen sørger for at denne String ikke har noget "whitespace", altså mellemrum eller newlines
                String year = scanner.next().trim();
                String genre = scanner.next().trim();
                String rating = scanner.next().trim();
                BufferedImage cover = null;

                // Tilføjer billedet til cover hvis dens filnavn indeholder filmens titel
                for (File pic : pics) {
                    if (pic.isFile()) {
                        String name = pic.getName().substring(0, pic.getName().length() - 4); // Fjerner ".jpg" fra billedernes navne

                        if (name.equalsIgnoreCase(title)) {
                            cover = ImageIO.read(pic);
                        }
                    }
                }

                // Kaster en exception hvis der ikke blev fundet et matchende cover til denne film
                if (cover == null) {
                    throw new NullPointerException("No photo matched the media: " + title);
                } else {
                    content.add(new Film(title, year, genre, rating, cover)); // Tilføjer et nyt Film-objekt til listen med de aflæste værdier
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Movie text file not found!");
        } catch (IOException e) {
            System.err.println("Could not read the image file!");
        } catch (NullPointerException e) {
            System.err.print(e.getMessage());
        }
    }

    public void fillSeries() {
        try {
            Scanner scanner = new Scanner(new File("resources/series_text.txt"))
                    .useLocale(Locale.forLanguageTag("en-DK"))
                    .useDelimiter("; |;");

            File folder = new File("resources/pics/Series");
            File[] pics = folder.listFiles();

            while (scanner.hasNext()) {
                Map<Integer, Integer> seasons = new HashMap<>(10);

                String title = scanner.next().trim();
                String year = scanner.next().trim();
                String genre = scanner.next().trim();
                String rating = scanner.next().trim();

                // Laver en ny scanner over den næste "bid" i dataen, altså rækken af sæsoner/episoder (et String af formatet "1-8, 2-22")
                Scanner seasonsScanner = new Scanner(scanner.next().trim())
                        .useDelimiter(", |,"); // Sætter nye symboler, som den skal afgrænse med (nu bliver dataen delt op med "1-8" og "2-22")

                // For hvert stykke data af formatet "1-8", gør følgende
                while (seasonsScanner.hasNext()) {
                    String[] seasonParsed = seasonsScanner.next().split("-"); // Del dataen op ved "-" symbolet og læg det i et Array (så [0] = 1, [1] = 8)

                    int season = Integer.parseInt(seasonParsed[0]);  // Deler arrayet op så jeg har de to tal i egne variabler, og konverterer direkte fra String til int
                    int episodes = Integer.parseInt(seasonParsed[1]);

                    seasons.put(season, episodes); // Mapper endelig de to værdier til hinanden og tilføjer til hashmappen
                }

                BufferedImage cover = null;

                for (File pic : pics) {
                    if (pic.isFile()) {
                        String name = pic.getName().substring(0, pic.getName().length() - 4);

                        if (name.equalsIgnoreCase(title)) {
                            cover = ImageIO.read(pic);
                        }
                    }
                }

                if (cover == null) {
                    throw new NullPointerException("No photo matched the media: " + title);
                } else {
                    content.add(new Series(title, year, genre, rating, cover, seasons));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Series text file not found!");
        } catch (NumberFormatException e) {
            System.err.println("Invalid season or episode value!");
        } catch (IOException e) {
            System.err.println("Could not read the image file!");
        } catch (NullPointerException e) {
            System.err.print(e.getMessage());
        }
    }
}
