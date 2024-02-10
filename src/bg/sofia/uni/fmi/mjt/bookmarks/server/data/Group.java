package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public record Group(String groupName, List<Bookmark> bookmarks) {
    public void addBookmark(Bookmark bookmark) {
        bookmarks.add(bookmark);
    }

    public boolean hasBookmark(String url) {
        return getBookmark(url) != null;
    }

    public Bookmark getBookmark(String url) {
        for (Bookmark current : bookmarks) {
            if (current.url().equals(url)) {
                return current;
            }
        }
        return null;
    }

    public boolean removeBookmark(String url) {
        Iterator<Bookmark> it = bookmarks.iterator();
        while (it.hasNext()) {
            Bookmark current = it.next();
            if (current.url().equals(url)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public int cleanUp() {
        int count = 0;
        Iterator<Bookmark> it = bookmarks.iterator();
        while (it.hasNext()) {
            Bookmark current = it.next();
            if (!current.valid()) {
                it.remove();
                count++;
            }
        }
        return count;
    }

    public static Group newGroup(String groupName) {
        return new Group(groupName, new LinkedList<Bookmark>());
    }
}
