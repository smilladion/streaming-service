import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Streaming service = new Streaming(new User("Bob"));
        service.fillCollection();
        service.showMedia();
    }
}
