package com.vjtechsolution.aiceluckywheel;

public class ProductRelData {

    public ProductRelData(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    private String kode, nama;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
