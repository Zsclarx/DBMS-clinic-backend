package com.example.demo1.dto;

public class AuthResponse {
    private String token;
    private String redirectUrl;
    private int UserID;

    public AuthResponse(String token, String redirectUrl,int userID) {
        this.token = token;
        this.redirectUrl = redirectUrl;
        this.UserID = userID;
    }

    public String getToken() {
        return token;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getUserID() {
        return UserID;
    }
}
