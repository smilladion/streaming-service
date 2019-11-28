import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Streaming {

    private ArrayList<Media> content;
    private ArrayList<User> users;
    private User primary;

    public Streaming(User user) {
        primary = user;
        users = new ArrayList<>();
        users.add(user);
        content = new ArrayList<>();
    }

    // Fylder listen med data, køres som det første i main. Smart!
    public void fillCollection() {
        try {
            Scanner scanner = new Scanner(new File("resources/movies_text.txt")) // Opretter ny scanner til data
                    .useLocale(Locale.forLanguageTag("en-DK")) // Ændrer sproget som aflæses, således at kommaer kan konverteres til punktummer (ift. ratings)
                    .useDelimiter("; |;"); // Afgrænser hvilke symboler som deler informationen op: enten "; " eller ";" (fordi datafilen er halvdårlig og ikke konsekvent med mellemrum)

            // Looper gennem hver linje i tekstfilen og sætter de individuelle data til deres specifikke felt
            while (scanner.hasNext()) {
                String title = scanner.next().trim(); //trim-funktionen sørger for at denne String ikke har noget "whitespace", altså mellemrum eller newlines
                int year = scanner.nextInt();
                String genre = scanner.next().trim();
                Float rating = scanner.nextFloat();

                content.add(new Film(title, year, genre, rating)); // Tilføjer et nyt Film-objekt til listen med de aflæste værdier
            }
        } catch (FileNotFoundException e) {
            System.out.println("Movie text file not found!");
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
