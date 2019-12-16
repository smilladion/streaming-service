package model;

import java.awt.image.BufferedImage;

public class Film extends Media {

    public Film(String title, String year, String genre, String rating, BufferedImage cover) {
        super(title, year, genre, rating, cover);
    }
}
