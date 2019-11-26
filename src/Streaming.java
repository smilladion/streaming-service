import java.util.*;

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
