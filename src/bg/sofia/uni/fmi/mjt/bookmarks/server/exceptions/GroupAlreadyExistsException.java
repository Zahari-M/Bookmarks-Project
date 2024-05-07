package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class GroupAlreadyExistsException extends UserException {
    public GroupAlreadyExistsException() {
    }

    public GroupAlreadyExistsException(String message) {
        super(message);
    }
}
