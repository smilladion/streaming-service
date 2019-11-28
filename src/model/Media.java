package model;

public abstract class Media {

    protected final String title;
    protected final String year; // Fordi vi ikke skal sortere efter årstal lige nu er det bare et string
    protected final String genre;
    protected final float rating;

    public Media(String title, String year, String genre, float rating) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public abstract String display();
}
