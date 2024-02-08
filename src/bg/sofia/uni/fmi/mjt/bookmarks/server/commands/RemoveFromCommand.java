package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import java.util.regex.Matcher;

public class RemoveFromCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int BOOKMARK_NUMBER = 2;
    private static final int ARGC = 3;

    RemoveFromCommand(String input) {
        Matcher matcher = CommandRegEx.REMOVE_FROM.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
        this.params[BOOKMARK_NUMBER] = matcher.group(BOOKMARK_NUMBER);
    }
}
