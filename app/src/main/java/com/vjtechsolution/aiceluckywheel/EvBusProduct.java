package com.vjtechsolution.aiceluckywheel;

public class EvBusProduct {
    private String product;
    private Integer qty;

    public EvBusProduct(String product, Integer qty) {
        this.product = product;
        this.qty = qty;
    }

    public String getProduct() {
        return product;
    }

    public Integer getQty() {
        return qty;
    }
}
