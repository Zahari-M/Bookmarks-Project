package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;

public class CleanupCommand extends Command {

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        storage.cleanup(userID);
    }
}
