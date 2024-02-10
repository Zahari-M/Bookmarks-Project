package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class BookmarkAlreadyExistsException extends RuntimeException {
    public BookmarkAlreadyExistsException() {
    }

    public BookmarkAlreadyExistsException(String message) {
        super(message);
    }
}
