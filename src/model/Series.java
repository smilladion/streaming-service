package model;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Series extends Media {

    private final Map<Integer, Integer> seasons; // Indeholder sæson nummer som key og antal episoder som value

    public Series(String title, String year, String genre, float rating, BufferedImage cover, Map<Integer, Integer> seasons) {
        super(title, year, genre, rating, cover);
        this.seasons = seasons;
    }

    public Map<Integer, Integer> getSeasons() {
        return seasons;
    }
}
