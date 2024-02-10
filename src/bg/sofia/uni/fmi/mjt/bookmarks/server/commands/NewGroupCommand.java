package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.regex.Matcher;

public class NewGroupCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int ARGC = 2;

    NewGroupCommand(String input) {
        Matcher matcher = CommandPattern.NEW_GROUP.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        storage.addNewGroup(params[GROUPNAME_NUMBER], userID);
    }
}
