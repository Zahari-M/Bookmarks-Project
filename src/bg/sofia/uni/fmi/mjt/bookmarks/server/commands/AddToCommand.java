package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.parser.BookmarkCreator;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.regex.Matcher;

public class AddToCommand extends Command {

    private static final int GROUPNAME_NUMBER = 1;
    private static final int BOOKMARK_NUMBER = 2;
    private static final int SHORTEN_NUMBER = 3;
    private static final int ARGC = 3;

    static BookmarkCreator bookmarkCreator = new BookmarkCreator(HttpClient.newHttpClient(),
        getBitlyToken());

    private boolean shorten = false;

    AddToCommand(String input) {
        Matcher matcher = CommandPattern.ADD_TO.getPattern().matcher(input);
        matcher.find();
        this.params = new String[ARGC];
        this.params[GROUPNAME_NUMBER] = matcher.group(GROUPNAME_NUMBER);
        this.params[BOOKMARK_NUMBER] = matcher.group(BOOKMARK_NUMBER);
        if (matcher.group(SHORTEN_NUMBER) != null) {
            shorten = true;
        }
    }

    @Override
    public void execute(Storage storage, int userID) throws IOException {
        Bookmark bookmark = bookmarkCreator.createBookmark(params[BOOKMARK_NUMBER], shorten);
        storage.addBookmarkTo(params[GROUPNAME_NUMBER], bookmark, userID);
    }

    @Override
    public String getSuccessMessage() {
        return "Bookmark added";
    }

    static String getBitlyToken() {
        return "123";
    }
}
