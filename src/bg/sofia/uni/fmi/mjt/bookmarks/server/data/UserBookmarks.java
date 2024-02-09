package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import java.util.List;

public record UserBookmarks(int userID, List<Group> groups) {
}
