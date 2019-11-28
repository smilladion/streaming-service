package model;

public class Series extends Media {

    private int[][] episodes;

    public Series(String title, int year, String genre, float rating, int[][] episodes) {
        super(title, year, genre, rating);
        this.episodes = episodes;
    }

    // Antal af sæsoner
    public int getSeasons() {
        return 0;
    }

    // Antal episoder i en specifik sæson
    public int getEpisodes(int season) {
        return 0;
    }
}
