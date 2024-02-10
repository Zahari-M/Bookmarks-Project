package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

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
            for (String tag : this.tags()) {
                if (searchTag.equalsIgnoreCase(tag)) {
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
        return this.title().contains(title);
    }

    public boolean valid() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response;
        response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == OKCODE;
    }

}
