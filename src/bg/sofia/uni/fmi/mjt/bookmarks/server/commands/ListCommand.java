package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;

public class ListCommand extends Command {
    public int execute(Storage storage, int userID) throws IOException {
        Bookmark bookmark = Bookmark.of(params[BOOKMARK_NUMBER], shorten);
        storage.addBookmarkTo(params[GROUPNAME_NUMBER], bookmark, userID);
        return userID;
    }
}
