package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import java.util.regex.Matcher;

public class ListGroupNameCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int ARGC = 2;

    ListGroupNameCommand(String input) {
        Matcher matcher = CommandRegEx.LIST_GROUP.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
    }
}
