package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import java.util.LinkedList;
import java.util.List;

public record Group(String groupName, List<Bookmark> bookmarks) {
    public static Group newGroup(String groupName) {
        return new Group(groupName, new LinkedList<Bookmark>());
    }
}
