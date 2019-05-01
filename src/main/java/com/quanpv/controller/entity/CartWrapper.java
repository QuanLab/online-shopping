package com.quanpv.controller.entity;


public class CartWrapper {

    private String token;
    private String productId;
    private String quantity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartWrapper{" +
                "token='" + token + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
