package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import java.util.regex.Matcher;

public class SearchTitleCommand extends Command {

    private static final int TITLE_NUMBER = 1;
    private static final int ARGC = 2;

    SearchTitleCommand(String input) {
        Matcher matcher = CommandRegEx.SEARCH_TITLE.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[TITLE_NUMBER] = matcher.group(TITLE_NUMBER);
    }
}
