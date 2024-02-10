package bg.sofia.uni.fmi.mjt.bookmarks.server.data;

public record User(String username, int passwordHash, int userID) {

    public boolean correctPassword(String password) {
        return passwordHash == password.hashCode();
    }

    public static User of(String username, String password, int userID) {
        return new User(username, password.hashCode(), userID);
    }
}
