package bg.sofia.uni.fmi.mjt.bookmarks.server.commands;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.parser.BookmarkCreator;
import bg.sofia.uni.fmi.mjt.bookmarks.server.storage.Storage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandTest {

    @Mock
    private Storage storage;

    private Command command;
    private static final int USERID = 1;
    private static final String group = "Cooking";
    private static final String url = "https://mykitchen.com";
    private static final String username = "Ivan";
    private static final String pass = "123";

    @Test
    void AddToTest() throws IOException, InterruptedException {
        Bookmark bookmark = new Bookmark(url, null, null);

        AddToCommand.bookmarkCreator = mock();
        when(AddToCommand.bookmarkCreator.createBookmark(url, false))
            .thenReturn(bookmark);

        command = Command.newCommand(String.format("add-to  %s  %s  ",group, url));
        command.execute(storage, USERID);
        verify(storage).addBookmarkTo(group, bookmark, USERID);


        AddToCommand.bookmarkCreator = new BookmarkCreator(HttpClient.newHttpClient(),
            AddToCommand.getBitlyToken());
    }

    @Test
    void cleanUpTest() throws IOException, InterruptedException {
        command = Command.newCommand("cleanup   ");
        command.execute(storage, USERID);
        verify(storage).cleanup(USERID);
    }

    @Test
    void listTest() throws IOException, InterruptedException {
        command = Command.newCommand("list  ");
        command.execute(storage, USERID);
        verify(storage).getAllBookmarks(USERID);
    }

    @Test
    void listGroupNameTest() throws IOException, InterruptedException {
        command = Command.newCommand(String.format("list  --group-name  %s  ", group));
        command.execute(storage, USERID);
        verify(storage).getAllBookmarksFromGroup(group, USERID);
    }

    @Test
    void loginCommandTest() throws IOException, InterruptedException {
        command = Command.newCommand(String.format("login  %s  %s ", username, pass));
        command.execute(storage, USERID);
        verify(storage).getUser(username, pass);
    }

    @Test
    void newGroupTest() throws IOException, InterruptedException {
        command = Command.newCommand(String.format("new-group   %s  ", group));
        command.execute(storage, USERID);
        verify(storage).addNewGroup(group, USERID);
    }
    @Test
    void registerTest() throws IOException, InterruptedException {
        command = Command.newCommand(String.format("register  %s  %s ", username, pass));
        command.execute(storage, USERID);
        verify(storage).addNewUser(username, pass);
    }

    @Test
    void removeFromTest() throws IOException, InterruptedException {
        command = Command.newCommand(String.format("remove-from  %s  %s", group, url));
        command.execute(storage, USERID);
        verify(storage).removeBookmarkFrom(group, url, USERID);
    }

    @Test
    void searchTagsTest() throws IOException, InterruptedException {
        List<String> tags = List.of("apple", "rice", "mushroom");
        command = Command.newCommand(String.format("search   --tags %s   %s   %s ", tags.toArray()));
        command.execute(storage, USERID);
        verify(storage).getBookmarksWithTags(tags, USERID);
    }

    @Test
    void searchTitleTest() throws IOException, InterruptedException {
        String title = "masterchef";
        command = Command.newCommand(String.format("search   --title  %s ", title));
        command.execute(storage, USERID);
        verify(storage).getBookmarksWithTitle(title, USERID);
    }

    @Test
    void nullCommandTest() throws IOException, InterruptedException {
        command = Command.newCommand("search --words 1 2 3");
        assertNull(command, "null command test");
    }

}