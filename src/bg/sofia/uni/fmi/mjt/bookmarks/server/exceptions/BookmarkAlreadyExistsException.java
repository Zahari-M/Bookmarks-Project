package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class BookmarkAlreadyExistsException extends UserException {
    public BookmarkAlreadyExistsException() {
    }

    public BookmarkAlreadyExistsException(String message) {
        super(message);
    }
}
