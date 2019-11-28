package model;

public class Film extends Media {

    public Film(String title, String year, String genre, float rating) {
        super(title, year, genre, rating);
    }

    @Override
    public String display() {
        return "Film name: " + title + ", Year: " + year + ", Genres: " + genre + ", Rating: " + rating;
    }
}
