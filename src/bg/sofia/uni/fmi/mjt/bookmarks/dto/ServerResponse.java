package bg.sofia.uni.fmi.mjt.bookmarks.dto;

import java.io.PrintStream;
import java.util.List;

public record ServerResponse(String message, List<BookmarkResponse> bookmarkResponses) {
    public void print(PrintStream printStream) {
        if (message != null) {
            printStream.println(message);
        }
        if (bookmarkResponses != null) {
            int i = 0;
            for (BookmarkResponse response : bookmarkResponses) {
                i++;
                printStream.printf("%d:\n", i);
                response.print(printStream);
            }
        }
    }
}
