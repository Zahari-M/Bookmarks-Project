package bg.sofia.uni.fmi.mjt.bookmarks.dto;

import java.io.PrintStream;

public record BookmarkResponse(String url, String title, String groupName) {
    public void print(PrintStream stream) {
        stream.printf("Group: %s\nTitle: %s\nURL: %s\n", groupName, title, url);
    }
}
