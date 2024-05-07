package bg.sofia.uni.fmi.mjt.bookmarks.server.storage;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.BookmarkResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkNotFoundException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.GroupAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.GroupNotFoundException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.IncorrectPasswordException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserNotFoundException;

import java.io.IOException;
import java.util.List;

public interface Storage {

    public int addNewUser(String username, String password) throws IOException, UserAlreadyExistsException;

    public int getUser(String username, String password) throws IOException, UserNotFoundException,
        IncorrectPasswordException;

    public void addNewGroup(String groupName, int userID) throws IOException, GroupAlreadyExistsException;

    public void addBookmarkTo(String groupName, Bookmark bookmark, int userID)
        throws IOException, GroupNotFoundException, BookmarkAlreadyExistsException;

    public void removeBookmarkFrom(String groupName, String url, int userID)
        throws IOException, GroupNotFoundException, BookmarkNotFoundException;

    public List<BookmarkResponse> getAllBookmarks(int userID) throws IOException;

    public List<BookmarkResponse> getAllBookmarksFromGroup(String groupName, int userID)
        throws IOException, GroupNotFoundException;

    public List<BookmarkResponse> getBookmarksWithTags(List<String> tag, int userID) throws IOException;

    public List<BookmarkResponse> getBookmarksWithTitle(String title, int userID) throws IOException;

    public int cleanup(int userID) throws IOException, InterruptedException;
}
