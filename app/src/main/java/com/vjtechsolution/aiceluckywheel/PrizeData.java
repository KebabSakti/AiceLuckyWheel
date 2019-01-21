package com.vjtechsolution.aiceluckywheel;

public class PrizeData {
    private String kode_asset, kode_produk;
    private ProductRelData product;

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
