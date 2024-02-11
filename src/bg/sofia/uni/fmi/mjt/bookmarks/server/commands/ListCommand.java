package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;

public class ListCommand extends Command {
    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        this.bookmarkResponses = storage.getAllBookmarks(userID);
    }

    @Override
    public String getSuccessMessage() {
        return String.format("You have %d bookmarks:", bookmarkResponses.size());
    }
}
