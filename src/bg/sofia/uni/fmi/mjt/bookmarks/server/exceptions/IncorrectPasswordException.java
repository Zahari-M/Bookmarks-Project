package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
