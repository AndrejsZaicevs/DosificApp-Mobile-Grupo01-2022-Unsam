package com.example.dosificapp.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String type; // P, A, F

    public LoggedInUser(String userId, String displayName, String type) {
        this.userId = userId;
        this.displayName = displayName;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getType() { return type; }
}