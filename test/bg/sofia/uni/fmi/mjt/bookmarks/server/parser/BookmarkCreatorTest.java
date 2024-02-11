package bg.sofia.uni.fmi.mjt.bookmarks.server.parser;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookmarkCreatorTest {

    private static final String uri = "https://www.uni-sofia.bg/index.php/eng";
    private static final String HTMLFile = "./uni-sofia.txt";
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<String> httpResponse;
    @org.junit.jupiter.api.Test
    void createBookmarkTest() throws Exception {
        String html = Files.readString(Path.of(HTMLFile));
        when(httpResponse.body()).thenReturn(html);
        when(httpResponse.statusCode()).thenReturn(200);

        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(uri)).build();

        when(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())).thenReturn(httpResponse);
        BookmarkCreator creator = new BookmarkCreator(httpClient, "123");
        Bookmark bookmark = creator.createBookmark(uri, false);
        assertTrue(bookmark.tags().contains("Sofia"), "Test Sofia keyword");
        assertTrue(bookmark.tags().contains("Education"), "Test Education keyword");
        assertEquals("Home - Софийски университет \"Св. Климент Охридски\"", bookmark.title(),
            "Test correct title");
        //System.out.println(bookmark);
    }

}