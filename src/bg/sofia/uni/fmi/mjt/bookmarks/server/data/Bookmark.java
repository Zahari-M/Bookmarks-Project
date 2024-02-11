package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import bg.sofia.uni.fmi.mjt.bookmarks.server.parser.BookmarkCreator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public record Bookmark(String url, String title, List<String> tags) {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static final int OKCODE = 200;
    public boolean containsTags(List<String> tags) {
        for (String searchTag : tags) {
            boolean found = false;
            String strippedSearchTag = BookmarkCreator.strip(searchTag);
            for (String tag : this.tags()) {
                if (searchTag.equalsIgnoreCase(tag) || strippedSearchTag.equalsIgnoreCase(tag)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }

    public boolean hasTitle(String title) {
        return this.title().toLowerCase().contains(title.toLowerCase());
    }

    public boolean valid() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == OKCODE;
    }

}
