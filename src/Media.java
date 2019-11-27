public class Media {

    private String title;
    private int year;
    private String genre;
    private float rating;

    public Media(String title, int year, String genre, float rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public String display() {
        return "Name: " + title + ", Year: " + year + ", Genres: " + genre + ", Rating: " + rating;
    }
}
