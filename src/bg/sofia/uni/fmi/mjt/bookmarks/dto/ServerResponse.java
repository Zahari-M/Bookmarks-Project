package bg.sofia.uni.fmi.mjt.bookmarks.dto;

public record ServerResponse(String message, BookmarkResponse[] bookmarkResponses) {
}
