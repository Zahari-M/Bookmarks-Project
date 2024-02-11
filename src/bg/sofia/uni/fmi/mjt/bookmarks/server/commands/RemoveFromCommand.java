package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.regex.Matcher;

public class RemoveFromCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int BOOKMARK_NUMBER = 2;
    private static final int ARGC = 3;

    RemoveFromCommand(String input) {
        Matcher matcher = CommandPattern.REMOVE_FROM.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
        this.params[BOOKMARK_NUMBER] = matcher.group(BOOKMARK_NUMBER);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        storage.removeBookmarkFrom(params[GROUPNAME_NUMBER], params[BOOKMARK_NUMBER], userID);
    }

    @Override
    public String getSuccessMessage() {
        return "Bookmark removed";
    }
}
