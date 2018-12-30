package com.vjtechsolution.aiceluckywheel;

public class UserAuth {
    private String username, password, api_token;

    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getApi_token() {
        return api_token;
    }
}
