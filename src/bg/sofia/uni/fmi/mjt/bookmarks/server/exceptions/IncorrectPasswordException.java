package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class IncorrectPasswordException extends UserException {
    public IncorrectPasswordException() {
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
