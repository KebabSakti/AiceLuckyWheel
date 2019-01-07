package com.vjtechsolution.aiceluckywheel;

public class DashboardModel {

    private String username, api_token, kode_asset, message;
    private Boolean status;
    private DashboardData data;

    public DashboardModel(String username, String api_token, String kode_asset) {
        this.username = username;
        this.api_token = api_token;
        this.kode_asset = kode_asset;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public DashboardData getData() {
        return data;
    }
}
