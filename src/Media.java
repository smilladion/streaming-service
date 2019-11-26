public class Media {

    // String som type er placeholder indtil vi får lavet GUI
    private String title;
    private String cover;
    private String genre;

    public Media(String title, String cover, String genre) {
        this.title = title;
        this.cover = cover;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }

    public String getGenre() {
        return genre;
    }
}
