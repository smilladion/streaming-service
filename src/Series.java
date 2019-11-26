public class Series extends Media {

    private int[][] episodes;

    public Series(String title, String cover, String genre, int[][] episodes) {
        super(title, cover, genre);
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
