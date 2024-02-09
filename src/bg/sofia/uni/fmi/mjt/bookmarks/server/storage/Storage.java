package bg.sofia.uni.fmi.mjt.bookmarks.server.storage;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.User;

public interface Storage {

    public int addNewUser(String username, String password);

    public int getUser(String username, String password);

    public void addNewGroup(String groupName);

    public void addBookmarkTo(String groupName, Bookmark bookmark);

    public void removeBookmarkFrom(String groupName, Bookmark bookmark);



}
