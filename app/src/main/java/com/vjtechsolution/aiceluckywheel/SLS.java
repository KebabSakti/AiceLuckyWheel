package com.vjtechsolution.aiceluckywheel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SLS {
    @SerializedName("data[]")
    @Expose
    private ArrayList<String> datas;
    private String status;

    public SLS(ArrayList<String> datas) {
        this.datas = datas;
    }

    public String getStatus() {
        return status;
    }
}
