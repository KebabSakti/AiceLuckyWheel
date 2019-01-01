package com.vjtechsolution.aiceluckywheel;

public class UserAuth {

    private String username, password, message;
    private Boolean status;
    private LoginData data;

    public UserAuth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LoginData getData() {
        return data;
    }
}
