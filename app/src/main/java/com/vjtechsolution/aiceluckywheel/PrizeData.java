package com.vjtechsolution.aiceluckywheel;

public class PrizeData {
    private String kode_asset, kode_produk;
    private Integer prize_stock;
    private ProductRelData product;

    public PrizeData(String kode_asset, String kode_produk, ProductRelData product) {
        this.kode_asset = kode_asset;
        this.kode_produk = kode_produk;
        this.product = product;
    }

    public Integer getPrize_stock() {
        return prize_stock;
    }

    public void setPrize_stock(Integer prize_stock) {
        this.prize_stock = prize_stock;
    }

    public String getKode_asset() {
        return kode_asset;
    }

    public void setKode_asset(String kode_asset) {
        this.kode_asset = kode_asset;
    }

    public String getKode_produk() {
        return kode_produk;
    }

    public void setKode_produk(String kode_produk) {
        this.kode_produk = kode_produk;
    }

    public ProductRelData getProduct() {
        return product;
    }

    public void setProduct(ProductRelData product) {
        this.product = product;
    }
}
