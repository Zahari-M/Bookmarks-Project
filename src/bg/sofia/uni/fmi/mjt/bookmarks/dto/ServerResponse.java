package bg.sofia.uni.fmi.mjt.bookmarks.dto;

import java.util.List;

public record ServerResponse(String message, List<BookmarkResponse> bookmarkResponses) {
}
