package model;

import model.exceptions.MediaAlreadyAFavouriteException;
import java.util.ArrayList;

public class User {

    private String name;
    private ArrayList<Media> favourites; // Giver hver oprettet bruger deres egen favoritliste af film

    public User(String name) {
        this.name = name;
        favourites = new ArrayList<>();
    }

    public ArrayList<Media> getFavourites() {
        return favourites;
    }

    public String getName() {
        return name;
    }

    // Tilføjer ny film/serie til favoritlisten
    public void addFavourite(Media media) {
        if (!favourites.contains(media)) {
            favourites.add(media);
        } else {
            throw new MediaAlreadyAFavouriteException();
        }
    }

    // Fjerner film/serie fra favoritlisten
    public void removeFavourite(Media media) {
        favourites.remove(media);
    }
}
