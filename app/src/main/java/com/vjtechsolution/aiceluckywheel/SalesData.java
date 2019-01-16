package com.vjtechsolution.aiceluckywheel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SalesData {
    private String username, api_token, kode_asset, foto, nama, no_telp, message, session;
    @SerializedName("products[]")
    @Expose
    private ArrayList<String> products;
    @SerializedName("qty_products[]")
    @Expose
    private ArrayList<Integer> qtyProducts;
    private Boolean status;

    public SalesData(String username, String api_token, String kode_asset, String foto, String nama, String no_telp, String session, ArrayList<String> products, ArrayList<Integer> qtyProducts) {
        this.username = username;
        this.api_token = api_token;
        this.kode_asset = kode_asset;
        this.foto = foto;
        this.nama = nama;
        this.no_telp = no_telp;
        this.session = session;
        this.products = products;
        this.qtyProducts = qtyProducts;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
