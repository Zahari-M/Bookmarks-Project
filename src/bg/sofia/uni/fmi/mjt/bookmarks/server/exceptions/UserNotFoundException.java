package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
