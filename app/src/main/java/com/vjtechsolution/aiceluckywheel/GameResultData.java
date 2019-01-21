package com.vjtechsolution.aiceluckywheel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameResultData {
    private String username, api_token, kode_asset, message, session, no_telp;
    private Boolean status;
    @SerializedName("beli[]")
    @Expose
    private ArrayList<Integer> beli;
    @SerializedName("drawn[]")
    @Expose
    private ArrayList<Integer> drawn;
    @SerializedName("menang[]")
    @Expose
    private ArrayList<Integer> menang;
    @SerializedName("kalah[]")
    @Expose
    private ArrayList<Integer> kalah;
    @SerializedName("hadiah[]")
    @Expose
    private ArrayList<String> hadiah;

    public GameResultData(String username, String api_token, String kode_asset, String session, String no_telp, ArrayList<Integer> beli, ArrayList<Integer> drawn, ArrayList<Integer> menang, ArrayList<Integer> kalah, ArrayList<String> hadiah) {
        this.username = username;
        this.api_token = api_token;
        this.kode_asset = kode_asset;
        this.session = session;
        this.no_telp = no_telp;
        this.beli = beli;
        this.drawn = drawn;
        this.menang = menang;
        this.kalah = kalah;
        this.hadiah = hadiah;
    }

    public String getMessage() {
        return message;
    }

    public String getSession() {
        return session;
    }
}
