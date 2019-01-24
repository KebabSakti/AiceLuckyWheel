package com.vjtechsolution.aiceluckywheel;

import java.util.List;

public class PrizeModel {
    private String username, api_token, kode_asset, message;
    private Boolean status;
    private List<PrizeData> data;

    public PrizeModel(String username, String api_token, String kode_asset) {
        this.username = username;
        this.api_token = api_token;
        this.kode_asset = kode_asset;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getKode_asset() {
        return kode_asset;
    }

    public void setKode_asset(String kode_asset) {
        this.kode_asset = kode_asset;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<PrizeData> getData() {
        return data;
    }

    public void setData(List<PrizeData> data) {
        this.data = data;
    }
}
