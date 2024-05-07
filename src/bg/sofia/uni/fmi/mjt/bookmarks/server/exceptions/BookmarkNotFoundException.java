package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

public class BookmarkNotFoundException extends UserException {
    public BookmarkNotFoundException() {
    }

    public BookmarkNotFoundException(String message) {
        super(message);
    }
}
