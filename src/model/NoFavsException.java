package model;

public class NoFavsException extends RuntimeException {
    public NoFavsException() {
        super("No media has been added to your favourites yet.");
    }
}
