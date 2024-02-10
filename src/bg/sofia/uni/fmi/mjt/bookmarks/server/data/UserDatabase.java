package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

import java.util.List;

public class UserDatabase {
    private int count;
    private final List<User> users;

    public UserDatabase(int count, List<User> users) {
        this.count = count;
        this.users = users;
    }

    public int addUser(String username, String password) {
        count++;
        users.add(User.of(username, password, count));
        return count;
    }

    public boolean exists(String username) {
        return getUser(username) != null;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.username().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
