package model;

import java.awt.image.BufferedImage;

public abstract class Media {

    protected final String title;
    protected final String year; // Fordi vi ikke skal sortere efter årstal lige nu er det bare et string
    protected final String genre;
    protected final float rating;
    protected final BufferedImage cover;

    public Media(String title, String year, String genre, float rating, BufferedImage cover) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
        this.cover = cover;
    }

    public abstract String display();

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public float getRating() {
        return rating;
    }

    public BufferedImage getCover() {
        return cover;
    }
}
