package bg.sofia.uni.fmi.mjt.bookmarks.server.parser;

import bg.sofia.uni.fmi.mjt.bookmarks.server.data.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BookmarkCreator {
    private static final int OKCODE = 200;
    private static final String[] SUFFIXES = {"ed", "ing", "ly"};
    private static final List<String> STOPWORDS = getStopwords();
    private static final String STOPWORDSDIR = "./common-english-words.txt";
    private static final String SPLITREGEX = "[\\s\\p{Punct}]+";
    private static final int TAGCOUNT = 7;
    private HttpClient httpClient;
    private final String bitlyToken;

    public BookmarkCreator(HttpClient httpClient, String bitlyToken) {
        this.httpClient = httpClient;
        this.bitlyToken = bitlyToken;
    }

    public Bookmark createBookmark(String url, boolean shortened) {
        String html = getHTML(url);
        Document document = Jsoup.parse(html);
        String title = document.title();
        String text = document.body().text();
        String[] words = text.split(SPLITREGEX);
        List<String> wordsFiltered = Arrays.stream(words).filter(BookmarkCreator::isNotStopWord)
            .map(BookmarkCreator::strip).toList();
        Map<String, Long> wordsCounted = wordsFiltered.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<String> tags = wordsCounted.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(TAGCOUNT)
            .map(Map.Entry::getKey).toList();
        if (shortened) {

        }
        return new Bookmark(url, title, tags);
    }

    private String getHTML(String url) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).build();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            throw new InvalidBookmarkException("Invalid bookmark", e);
        }
        if (response.statusCode() != OKCODE) {
            throw new InvalidBookmarkException("Invalid bookmark");
        }
        return response.body();
    }

    public static String strip(String word) {
        for (String suffix : SUFFIXES) {
            if (word.endsWith(suffix)) {
                return word.substring(0, word.length() - suffix.length());
            }
        }
        return word;
    }

    private static List<String> getStopwords() {
        try {
            return Arrays.asList(Files.readString(Paths.get(STOPWORDSDIR)).split(","));
        } catch (Exception e) {
            throw new RuntimeException("File not found: " + STOPWORDSDIR, e);
        }
    }

    private static boolean isNotStopWord(String str) {
        for (String word : STOPWORDS) {
            if (word.equalsIgnoreCase(str)) {
                return false;
            }
        }
        return true;
    }

}
