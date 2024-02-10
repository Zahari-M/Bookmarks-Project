package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.BookmarkResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.List;

public abstract class Command {
    protected String[] params;
    protected int userID = -1;
    protected List<BookmarkResponse> bookmarkResponses;
    public abstract void execute(Storage storage, int userID) throws IOException, InterruptedException;

    public int getUserID() {
        return userID;
    }

    public List<BookmarkResponse> getBookmarkResponses() {
        return bookmarkResponses;
    }

    public static Command newCommand(String input) {
        for (CommandPattern comPattern : CommandPattern.values()) {
            if (comPattern.getPattern().matcher(input).matches()) {
                return comPattern.getConstructor().apply(input);
            }
        }
        return null;
    }
}
