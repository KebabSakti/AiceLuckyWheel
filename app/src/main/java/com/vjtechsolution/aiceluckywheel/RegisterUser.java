package com.vjtechsolution.aiceluckywheel;

public class RegisterUser {
    private String username, namaToko, message;
    private Boolean status;
    private Float lat, lng;

    public RegisterUser(String username, String namaToko, Float lat, Float lng) {
        this.username = username;
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
