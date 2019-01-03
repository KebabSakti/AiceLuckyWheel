package com.vjtechsolution.aiceluckywheel;

public class RegisterUser {
    private String username, password, namaToko, message;
    private Boolean status;
    private Double lat, lng;

    public RegisterUser(String username, String password, String namaToko, Double lat, Double lng) {
        this.username = username;
        this.password = password;
        this.namaToko = namaToko;
        this.lat = lat;
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
