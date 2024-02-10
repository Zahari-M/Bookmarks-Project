package bg.sofia.uni.fmi.mjt.bookmarks.server.storage;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.BookmarkResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;

import java.io.IOException;
import java.util.List;

public interface Storage {

    public int addNewUser(String username, String password) throws IOException;

    public int getUser(String username, String password) throws IOException;

    public void addNewGroup(String groupName, int userID) throws IOException;

    public void addBookmarkTo(String groupName, Bookmark bookmark, int userID) throws IOException;

    public void removeBookmarkFrom(String groupName, String url, int userID) throws IOException;

    public List<BookmarkResponse> getAllBookmarks(int userID) throws IOException;

    public List<BookmarkResponse> getAllBookmarksFromGroup(String groupName, int userID) throws IOException;

    public List<BookmarkResponse> getBookmarksWithTags(List<String> tag, int userID) throws IOException;

    public List<BookmarkResponse> getBookmarksWithTitle(String title, int userID) throws IOException;

    public int cleanup(int userID) throws IOException, InterruptedException;
}
