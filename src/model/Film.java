package model;

import java.awt.image.BufferedImage;

public class Film extends Media {

    public Film(String title, String year, String genre, float rating, BufferedImage cover) {
        super(title, year, genre, rating, cover);
    }
}
