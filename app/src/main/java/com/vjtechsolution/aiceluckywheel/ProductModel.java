package com.vjtechsolution.aiceluckywheel;

import java.util.List;

public class ProductModel {

    private String username, api_token, message;
    private Boolean status;
    private List<ProductData> productDataList;

    public ProductModel(String username, String api_token) {
        this.username = username;
        this.api_token = api_token;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<ProductData> getProductDataList() {
        return productDataList;
    }
}
