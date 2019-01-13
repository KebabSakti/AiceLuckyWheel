package com.vjtechsolution.aiceluckywheel;

import java.util.List;

public class ProductModel {

    private String username, api_token, kode_asset, message;
    private Boolean status;
    private List<ProductData> data;

    public ProductModel(String username, String api_token, String kode_asset) {
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

    public List<ProductData> getProductDataList() {
        return data;
    }
}
