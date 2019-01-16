package com.vjtechsolution.aiceluckywheel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SLS {
    @SerializedName("data[]")
    @Expose
    private String username, api_token, kode_asset, message;
    private ArrayList<String> data;
    boolean status;


}
