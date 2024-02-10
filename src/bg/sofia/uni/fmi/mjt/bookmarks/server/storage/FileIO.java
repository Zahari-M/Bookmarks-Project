package bg.sofia.uni.fmi.mjt.bookmarks.server.storage;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.UserBookmarks;
import bg.sofia.uni.fmi.mjt.bookmarks.server.data.UserDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class FileIO {
    private static final String USERS_FILE = "users.txt";
    private static final Gson GSON = new Gson();
    private final Path mainDir;
    private final Path usersDir;

    public FileIO(String path) throws IOException {
        this.mainDir = Path.of(path);
        this.usersDir = mainDir.resolve(USERS_FILE);
        setUp();
    }

    void setUp() throws IOException {
        Files.createDirectories(mainDir);
        if (Files.notExists(usersDir)) {
            UserDatabase emptydb = new UserDatabase(0, new LinkedList<>());
            writeUsers(emptydb);
        }
    }

    UserDatabase readUsers() throws IOException {
        return GSON.fromJson(Files.readString(usersDir), UserDatabase.class);
    }

    void writeUsers(UserDatabase db) throws IOException {
        Files.writeString(usersDir, GSON.toJson(db, UserDatabase.class));
    }

    void addBookmarksFile(int userID) throws IOException {
        writeBookmarks(new UserBookmarks(userID, new LinkedList<>()), userID);
    }

    UserBookmarks readBookmarks(int userID) throws IOException {
        Path path = bookmarkDir(userID);
        return GSON.fromJson(Files.readString(path), UserBookmarks.class);
    }

    void writeBookmarks(UserBookmarks bookmarks, int userID) throws IOException {
        Path path = bookmarkDir(userID);
        Files.writeString(path, GSON.toJson(bookmarks, UserBookmarks.class));
    }

    private Path bookmarkDir(int userID) {
        return mainDir.resolve(Integer.toString(userID));
    }
}
