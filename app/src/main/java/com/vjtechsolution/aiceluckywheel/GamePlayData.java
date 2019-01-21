package com.vjtechsolution.aiceluckywheel;

public class GamePlayData {
    private String session, no_telp, kode_asset, hadiah;
    private Integer beli, drawn, menang, kalah;

    public GamePlayData(String session, String no_telp, String kode_asset, String hadiah, Integer beli, Integer drawn, Integer menang, Integer kalah) {
        this.session = session;
        this.no_telp = no_telp;
        this.kode_asset = kode_asset;
        this.hadiah = hadiah;
        this.beli = beli;
        this.drawn = drawn;
        this.menang = menang;
        this.kalah = kalah;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getKode_asset() {
        return kode_asset;
    }

    public void setKode_asset(String kode_asset) {
        this.kode_asset = kode_asset;
    }

    public String getHadiah() {
        return hadiah;
    }

    public void setHadiah(String hadiah) {
        this.hadiah = hadiah;
    }

    public Integer getBeli() {
        return beli;
    }

    public void setBeli(Integer beli) {
        this.beli = beli;
    }

    public Integer getDrawn() {
        return drawn;
    }

    public void setDrawn(Integer drawn) {
        this.drawn = drawn;
    }

    public Integer getMenang() {
        return menang;
    }

    public void setMenang(Integer menang) {
        this.menang = menang;
    }

    public Integer getKalah() {
        return kalah;
    }

    public void setKalah(Integer kalah) {
        this.kalah = kalah;
    }
}
