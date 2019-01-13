package com.vjtechsolution.aiceluckywheel;

import java.util.HashMap;

public class CustomerSalesDetail {

    private HashMap<String, Integer> productQty;
    private String foto, no_tlp, nama;

    public HashMap<String, Integer> getProductQty() {
        return productQty;
    }

    public void setProductQty(HashMap<String, Integer> productQty) {
        this.productQty = productQty;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
