package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.regex.Matcher;

public class ListGroupNameCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int ARGC = 2;

    ListGroupNameCommand(String input) {
        Matcher matcher = CommandPattern.LIST_GROUP.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        this.bookmarkResponses = storage.getAllBookmarksFromGroup(params[GROUPNAME_NUMBER], userID);
    }

    @Override
    public String getSuccessMessage() {
        return String.format("%d bookmarks in group %s:", bookmarkResponses.size(),
            params[GROUPNAME_NUMBER]);
    }
}
