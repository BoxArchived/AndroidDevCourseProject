package dev.boxz.kingofkaraoke;

public class User {
    private String accessToken;
    private String idToken;
    private String username;
    private String email;

    public User() {
    }

    public User(String accessToken, String idToken, String username, String email) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.username = username;
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
