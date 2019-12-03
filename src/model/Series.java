package model;

import java.awt.image.BufferedImage;
import java.util.Map;

public class Series extends Media {

    private final Map<Integer, Integer> seasons;

    public Series(String title, String year, String genre, float rating, BufferedImage cover, Map<Integer, Integer> seasons) {
        super(title, year, genre, rating, cover);
        this.seasons = seasons;
    }

    @Override
    public String display() {
        String seasonList = "";

        for (Map.Entry<Integer, Integer> season : seasons.entrySet()) {
            seasonList += season.getKey() + "-" + season.getValue() + ", ";
        }

        // Kompliceret alternativ til at fjerne det sidste komma (vi behøver det nok ikke siden dette er til testing)
        /*
        String seasonList = seasons.entrySet().stream()
                .map(season -> season.getKey() + "-" + season.getValue())
                .collect(Collectors.joining(", "));
        */

        return "Series name: " + title + ", Year: " + year + ", Genres: " + genre + ", Rating: " + rating + ", Seasons: " + seasonList;
    }

    public Map<Integer, Integer> getSeasons() {
        return seasons;
    }
}
