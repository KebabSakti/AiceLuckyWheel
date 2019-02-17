package com.vjtechsolution.aiceluckywheel;

public class AdsModel {
    private String username, api_token, kode_asset, message;
    private Boolean status;
    private AdsData data;

    public AdsModel(String username, String api_token, String kode_asset) {
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

    public AdsData getData() {
        return data;
    }
}
