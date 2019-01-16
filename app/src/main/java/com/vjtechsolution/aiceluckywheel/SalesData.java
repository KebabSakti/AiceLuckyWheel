package com.vjtechsolution.aiceluckywheel;

import java.util.HashMap;
import java.util.List;

public class SalesData {
    private String username, api_token, kode_asset, foto, nama, no_telp, message;
    private HashMap<String, Integer> listProduk;
    private Boolean status;

    public SalesData(String username, String api_token, String kode_asset, String foto, String nama, String no_telp, HashMap<String, Integer> listProduk) {
        this.username = username;
        this.api_token = api_token;
        this.kode_asset = kode_asset;
        this.foto = foto;
        this.nama = nama;
        this.no_telp = no_telp;
        this.listProduk = listProduk;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}
