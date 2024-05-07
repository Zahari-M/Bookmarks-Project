package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class GroupNotFoundException extends UserException {
    public GroupNotFoundException() {
    }

    public GroupNotFoundException(String message) {
        super(message);
    }
}
