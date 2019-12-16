package model;

import java.awt.image.BufferedImage;

public abstract class Media {

    protected final String title;
    protected final String year; // Fordi vi ikke sorterer efter årstal er dette bare en String.
    protected final String genre; // Indeholder alle genrer, som mediet har, så vi bare kan tjekke med .contains() senere.
    protected final String rating; // Denne er en float i tilfælde af at vi gerne ville sortere efter rating.
    protected final BufferedImage cover;

    public Media(String title, String year, String genre, String rating, BufferedImage cover) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getRating() {
        return rating;
    }

    public BufferedImage getCover() {
        return cover;
    }
}
