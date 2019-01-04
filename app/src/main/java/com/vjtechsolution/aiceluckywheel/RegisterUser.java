package com.vjtechsolution.aiceluckywheel;

public class RegisterUser {

    private String username, password, nama_toko, message;
    private Boolean status;
    private Double lat, lng;

    public RegisterUser(String username, String password, String nama_toko, Double lat, Double lng) {
        this.username = username;
        this.password = password;
        this.nama_toko = nama_toko;
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
