package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class UserAlreadyExistsException extends UserException {
    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
