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
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileSystemStorageTest {

    private static String groupName = "uni";
    private static String wrongGroupName = "qwerty";
    private static String pass = "123";
    private static String username = "Ivan";
    private static User user = User.of(username, pass, 1);
    private static Bookmark bookmark;
    private Group group, emptyGroup;
    private UserBookmarks userBookmarks, userBookmarksEmptyGroup, userBookmarksEmpty;
    private UserDatabase users, emptyUsers;
    private List<BookmarkResponse> correctResponse;
    private static final int USERID1 = 1;
    @Mock
    private FileIO fileIO;
    @InjectMocks
    private FileSystemStorage fileSystemStorage;

    @Test
    void addNewUser() throws IOException, UserAlreadyExistsException {
        when(fileIO.readUsers()).thenReturn(emptyUsers);
        int id = fileSystemStorage.addNewUser(username, pass);
        assertEquals(USERID1, id, "Add user correct id test");
        verify(fileIO).writeUsers(users);
        verify(fileIO).addBookmarksFile(USERID1);
    }

    @Test
    void getUser() throws IOException, UserNotFoundException, IncorrectPasswordException {
        when(fileIO.readUsers()).thenReturn(users);
        int id = fileSystemStorage.getUser(username, pass);
        assertEquals(USERID1, id, "Test get user with id 1");
    }

    @Test
    void addNewGroup() throws IOException, GroupAlreadyExistsException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarksEmpty);
        fileSystemStorage.addNewGroup(groupName, USERID1);
        verify(fileIO).writeBookmarks(userBookmarksEmptyGroup, USERID1);
    }

    @Test
    void addBookmarkTo() throws IOException, GroupNotFoundException, BookmarkAlreadyExistsException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarksEmptyGroup);
        fileSystemStorage.addBookmarkTo(groupName, bookmark, USERID1);
        verify(fileIO).writeBookmarks(userBookmarks, USERID1);
    }

    @Test
    void removeBookmarkFrom() throws IOException, BookmarkNotFoundException, GroupNotFoundException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarks);
        fileSystemStorage.removeBookmarkFrom(groupName, bookmark.url(), USERID1);
        verify(fileIO).writeBookmarks(userBookmarksEmptyGroup, USERID1);
    }

    @Test
    void getAllBookmarks() throws IOException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarks);
        List<BookmarkResponse> responses =
            fileSystemStorage.getAllBookmarks(USERID1);
        assertEquals(correctResponse, responses, "Test get all bookmarks correct response");
    }

    @Test
    void getAllBookmarksFromGroup() throws IOException, GroupNotFoundException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarks);
        List<BookmarkResponse> responses =
            fileSystemStorage.getAllBookmarksFromGroup(groupName, USERID1);
        assertEquals(correctResponse, responses, "Test get all bookmarks from uni");
        assertThrows(GroupNotFoundException.class, () -> fileSystemStorage.getAllBookmarksFromGroup(wrongGroupName, USERID1)
                  , "test wrong group query");
    }

    @Test
    void getBookmarksWithTags() throws IOException{
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarks);
        List<BookmarkResponse> responses =
            fileSystemStorage.getBookmarksWithTags(List.of("soFia", "uniVeRsity"), USERID1);
        assertEquals(correctResponse, responses, "Test get with tags Sofia University");
        assertTrue(fileSystemStorage.getBookmarksWithTags(List.of("Tomatoes"), USERID1)
            .isEmpty(), "test wrong tag query");
    }

    @Test
    void getBookmarksWithTitle() throws IOException {
        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarks);
        List<BookmarkResponse> responses =
            fileSystemStorage.getBookmarksWithTitle("Софийски университет", USERID1);
        assertEquals(correctResponse, responses, "Test get with title су");
        assertTrue(fileSystemStorage.getBookmarksWithTitle("Dogs", USERID1)
            .isEmpty(), "test wrong title query");
    }

    @Test
    void cleanup() throws Exception{
        UserBookmarks userBookmarksMocked;
        Bookmark mockedBookmark = mock();
        when(mockedBookmark.valid()).thenReturn(false);
        userBookmarksMocked = new UserBookmarks(1, new LinkedList<>(
            List.of(new Group(groupName, new LinkedList<>(List.of(mockedBookmark))))));

        when(fileIO.readBookmarks(USERID1)).thenReturn(userBookmarksMocked);
        fileSystemStorage.cleanup(USERID1);
        verify(fileIO).writeBookmarks(userBookmarksEmptyGroup, USERID1);
    }

    @BeforeEach
    void setUpData() {
        emptyGroup = new Group(groupName, new LinkedList<>());
        group = new Group(groupName, new LinkedList<>(List.of(bookmark)));
        emptyUsers = new UserDatabase(0, new LinkedList<>());
        users = new UserDatabase(1, new LinkedList<>(List.of(user)));
        userBookmarksEmptyGroup = new UserBookmarks(1, new LinkedList<>(List.of(emptyGroup)));
        userBookmarksEmpty = new UserBookmarks(1, new LinkedList<>());
        userBookmarks = new UserBookmarks(1, new LinkedList<>(List.of(group)));
        correctResponse = new LinkedList<>();
        correctResponse.add(new BookmarkResponse(bookmark.url(), bookmark.title(), groupName));

    }

    @BeforeAll
    static void setUpStatic() {
        bookmark = new Bookmark("https://www.uni-sofia.bg/index.php/eng",
            "Home - Софийски университет \"Св. Климент Охридски\"",
            List.of("Sofia","University","Education"));
    }
}