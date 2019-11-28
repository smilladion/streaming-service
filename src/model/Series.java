package model;

import java.util.Map;

public class Series extends Media {

    private final Map<Integer, Integer> seasons;

    public Series(String title, String year, String genre, float rating, Map<Integer, Integer> seasons) {
        super(title, year, genre, rating);
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

    // Antal af sæsoner
    public Map<Integer, Integer> getSeasons() {
        return seasons;
    }

    // Antal episoder i en specifik sæson
    public int getEpisodes(int season) {
        return 0;
    }
}
