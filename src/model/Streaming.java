package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Streaming {

    private final ArrayList<Media> content;
    private final ArrayList<User> users;
    private User primary;

    public Streaming(User user) {
        primary = user;
        users = new ArrayList<>();
        users.add(user);
        content = new ArrayList<>();
    }

    // Fylder listen med data, køres som det første i main. Smart!
    public void fillMovies() {
        try {
            Scanner scanner = new Scanner(new File("resources/movies_text.txt")) // Opretter ny scanner til data
                    .useLocale(Locale.forLanguageTag("en-DK")) // Ændrer sproget som aflæses, således at kommaer kan konverteres til punktummer (ift. ratings)
                    .useDelimiter("; |;"); // Afgrænser hvilke symboler som deler informationen op: enten "; " eller ";" (fordi datafilen er halvdårlig og ikke konsekvent med mellemrum)

            // Looper gennem hver linje i tekstfilen og sætter de individuelle data til deres specifikke felt
            while (scanner.hasNext()) {
                String title = scanner.next().trim(); // Trim-funktionen sørger for at denne String ikke har noget "whitespace", altså mellemrum eller newlines
                String year = scanner.next().trim();
                String genre = scanner.next().trim();
                float rating = scanner.nextFloat();

                content.add(new Film(title, year, genre, rating)); // Tilføjer et nyt Film-objekt til listen med de aflæste værdier
            }
        } catch (FileNotFoundException e) {
            System.err.println("Movie text file not found!");
        }
    }

    public void fillSeries() {
        try {
            Scanner scanner = new Scanner(new File("resources/series_text.txt"))
                    .useLocale(Locale.forLanguageTag("en-DK"))
                    .useDelimiter("; |;");

            while (scanner.hasNext()) {
                Map<Integer, Integer> seasons = new HashMap<>(10);

                String title = scanner.next().trim();
                String year = scanner.next().trim();
                String genre = scanner.next().trim();
                float rating = scanner.nextFloat();

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

                content.add(new Series(title, year, genre, rating, seasons));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Series text file not found!");
        } catch (NumberFormatException e) {
            System.err.println("Invalid season or episode value!");
        }
    }

    // Printer alle film/serier i listen til terminalen, for testing purposes
    public void showMedia() {
        for (Media media : content) {
            System.out.println(media.display());
        }
    }

    public ArrayList<Media> getContent() {
        return content;
    }

    // Sorterer medierne efter film/serie (evt. ved brug af instanceof if-statements)
    public void sortType(String type) {

    }

    // Sorterer medierne efter den valgte genre
    public void sortGenre(String genre) {

    }

    // Søger efter medier gennem titler
    public void searchTitle(String title) {

    }

    // Afspiller ikke "rigtigt", men viser noget simpelt hvis brugeren forsøger at afspille
    public void playMedia() {

    }

    // Tilføjer ny bruger til listen, evt. med restriktioner på hvor mange man kan tilføje
    public void addUser(User user) {
        users.add(user);
    }

    // Fjerner en bestemt bruger fra listen, evt. med restriktion på at man ikke kan slette den primære bruger
    public void removeUser(User user) {

    }

    // Skift ens primære bruger til at være en ny fra brugerlisten (users)
    public void changeUser(User user) {

    }
}
