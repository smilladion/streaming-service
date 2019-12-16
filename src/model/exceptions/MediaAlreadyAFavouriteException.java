package model.exceptions;

public class MediaAlreadyAFavouriteException extends RuntimeException {
    public MediaAlreadyAFavouriteException() {
        super("This media has already been added to your favourites list!");
    }
}
