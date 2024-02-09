package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class GroupAlreadyExistsException extends RuntimeException {
    public GroupAlreadyExistsException() {
    }

    public GroupAlreadyExistsException(String message) {
        super(message);
    }
}
