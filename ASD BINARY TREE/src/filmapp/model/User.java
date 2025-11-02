package filmapp.model;

public class User {
    private String userId;
    private String username;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }

    @Override
    public String toString() {
        return username + " (" + userId + ")";
    }
}
