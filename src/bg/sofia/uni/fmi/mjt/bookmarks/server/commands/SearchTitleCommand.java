package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.regex.Matcher;

public class SearchTitleCommand extends Command {

    private static final int TITLE_NUMBER = 1;
    private static final int ARGC = 2;

    SearchTitleCommand(String input) {
        Matcher matcher = CommandPattern.SEARCH_TITLE.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[TITLE_NUMBER] = matcher.group(TITLE_NUMBER);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        this.bookmarkResponses = storage.getBookmarksWithTitle(params[TITLE_NUMBER], userID);
    }

    @Override
    public String getSuccessMessage() {
        return String.format("%d bookmarks found:", bookmarkResponses.size());
    }
}
