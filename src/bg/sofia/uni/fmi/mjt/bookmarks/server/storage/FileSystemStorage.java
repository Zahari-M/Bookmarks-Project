package bg.sofia.uni.fmi.mjt.bookmarks.server.storage;

import bg.sofia.uni.fmi.mjt.bookmarks.dto.BookmarkResponse;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.UserBookmarks;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.UserDatabase;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkNotFoundException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.GroupAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.GroupNotFoundException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.IncorrectPasswordException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserNotFoundException;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class FileSystemStorage implements Storage {

    private final FileIO fileIO;
    public FileSystemStorage(FileIO fileIO) {
        this.fileIO = fileIO;
    }

    @Override
    public int addNewUser(String username, String password) throws IOException {
        UserDatabase db = fileIO.readUsers();
        if (db.exists(username)) {
            throw new UserAlreadyExistsException("User already exists");
        }
        int id = db.addUser(username, password);
        fileIO.writeUsers(db);
        fileIO.addBookmarksFile(id);
        return id;
    }

    @Override
    public int getUser(String username, String password) throws IOException {
        UserDatabase db = fileIO.readUsers();
        User user = db.getUser(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!user.correctPassword(password)) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return user.userID();
    }

    @Override
    public void addNewGroup(String groupName, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        if (bookmarks.existsGroup(groupName)) {
            throw new GroupAlreadyExistsException("Group already exists");
        }
        bookmarks.addGroup(groupName);
        fileIO.writeBookmarks(bookmarks, userID);
    }

    @Override
    public void addBookmarkTo(String groupName, Bookmark bookmark, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        Group group = bookmarks.getGroup(groupName);
        if (group == null) {
            throw new GroupNotFoundException("Group not found.");
        }
        if (group.hasBookmark(bookmark.url())) {
            throw new BookmarkAlreadyExistsException("Bookmark already exists.");
        }
        group.addBookmark(bookmark);
        fileIO.writeBookmarks(bookmarks, userID);
    }

    @Override
    public void removeBookmarkFrom(String groupName, String url, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        Group group = bookmarks.getGroup(groupName);
        if (group == null) {
            throw new GroupNotFoundException("Group not found.");
        }
        if (!group.removeBookmark(url)) {
            throw new BookmarkNotFoundException("Bookmark not found.");
        }
        fileIO.writeBookmarks(bookmarks, userID);
    }

    @Override
    public List<BookmarkResponse> getAllBookmarks(int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        List<BookmarkResponse> responses = new LinkedList<>();
        for (Group group : bookmarks.groups()) {
            for (Bookmark bookmark : group.bookmarks()) {
                responses.add(new BookmarkResponse(bookmark.url(), bookmark.title(), group.groupName()));
            }
        }
        return responses;
    }

    @Override
    public List<BookmarkResponse> getAllBookmarksFromGroup(String groupName, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        List<BookmarkResponse> responses = new LinkedList<>();
        Group group = bookmarks.getGroup(groupName);
        if (group == null) {
            throw new GroupNotFoundException("Group not found.");
        }
        for (Bookmark bookmark : group.bookmarks()) {
            responses.add(new BookmarkResponse(bookmark.url(), bookmark.title(), group.groupName()));
        }
        return responses;
    }

    @Override
    public List<BookmarkResponse> getBookmarksWithTags(List<String> tags, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        List<BookmarkResponse> responses = new LinkedList<>();
        for (Group group : bookmarks.groups()) {
            for (Bookmark bookmark : group.bookmarks()) {
                if (bookmark.containsTags(tags)) {
                    responses.add(new BookmarkResponse(bookmark.url(), bookmark.title(), group.groupName()));
                }
            }
        }
        return responses;
    }

    @Override
    public List<BookmarkResponse> getBookmarksWithTitle(String title, int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        List<BookmarkResponse> responses = new LinkedList<>();
        for (Group group : bookmarks.groups()) {
            for (Bookmark bookmark : group.bookmarks()) {
                if (bookmark.hasTitle(title)) {
                    responses.add(new BookmarkResponse(bookmark.url(), bookmark.title(), group.groupName()));
                }
            }
        }
        return responses;
    }

    @Override
    public int cleanup(int userID) throws IOException {
        UserBookmarks bookmarks = fileIO.readBookmarks(userID);
        int count = 0;
        for (Group group : bookmarks.groups()) {
            count += group.cleanUp();
        }
        fileIO.writeBookmarks(bookmarks, userID);
        return count;
    }

}
