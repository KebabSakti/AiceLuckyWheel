package com.vjtechsolution.aiceluckywheel;

public class EvBusProduct {
    private String product = "";
    private Integer qty = 0;
    private String foto = "";
    private String nama = "";
    private String no_telp = "";

    public EvBusProduct(String product, Integer qty, String foto, String nama, String no_telp) {
        this.product = product;
        this.qty = qty;
        this.foto = foto;
        this.nama = nama;
        this.no_telp = no_telp;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQty() {
        return qty;
    }

    public String getFoto() {
        return foto;
    }

    public String getNama() {
        return nama;
    }

    public String getNo_telp() {
        return no_telp;
    }
}
