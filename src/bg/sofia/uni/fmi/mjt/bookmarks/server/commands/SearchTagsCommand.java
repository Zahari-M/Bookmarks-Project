package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchTagsCommand extends Command {

    private static final int TAG_NUMBER = 1;
    private static final Pattern PARAM_PATTERN = Pattern.compile("(?:^search +--tags)? +(\\S+)");

    SearchTagsCommand(String input) {
        Matcher matcher = PARAM_PATTERN.matcher(input);
        List<String> params = new LinkedList<>();
        while (matcher.find()) {
            params.add(matcher.group(TAG_NUMBER));
        }
        this.params = params.toArray(new String[0]);
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException, InterruptedException {
        this.bookmarkResponses = storage.getBookmarksWithTags(List.of(params), userID);
    }

    @Override
    public String getSuccessMessage() {
        return String.format("%d bookmarks found:", bookmarkResponses.size());
    }
}
