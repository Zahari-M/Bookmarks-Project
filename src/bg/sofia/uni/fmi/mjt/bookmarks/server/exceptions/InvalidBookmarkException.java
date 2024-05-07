package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class InvalidBookmarkException extends UserException {
    public InvalidBookmarkException() {
    }

    public InvalidBookmarkException(String message) {
        super(message);
    }

    public InvalidBookmarkException(String message, Throwable cause) {
        super(message, cause);
    }
}
