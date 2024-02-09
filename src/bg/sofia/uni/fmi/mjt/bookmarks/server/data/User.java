package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

public record User(String username, int passwordHash, int userID) {
    public static User of(String username, String password, int userID) {
        return new User(username, password.hashCode(), userID);
    }
}
