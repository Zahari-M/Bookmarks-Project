package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import java.util.LinkedList;
import java.util.List;

public record UserBookmarks(int userID, List<Group> groups) {

    public void addGroup(String groupName) {
        groups.add(new Group(groupName, new LinkedList<>()));
    }

    public Group getGroup(String groupName) {
        for (Group group : groups) {
            if (group.groupName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public boolean existsGroup(String groupName) {
        return getGroup(groupName) != null;
    }
}
