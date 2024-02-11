package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;

public class CleanupCommand extends Command {
    private int cleared;

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        cleared = storage.cleanup(userID);
    }

    @Override
    public String getSuccessMessage() {
        return String.format("%d invalid bookmarks cleared", cleared);
    }
}
