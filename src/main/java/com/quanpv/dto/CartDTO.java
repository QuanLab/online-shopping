package com.quanpv.dto;


import com.quanpv.model.Item;

import java.util.List;


public class CartDTO {

    private String cartId;
    private List<Item> items;
    private float subtotal;

    public CartDTO(String cartId, List<Item> items) {
        this.cartId = cartId;
        this.items = items;
        subtotal =0;
        for(Item item: items) {
            subtotal+= item.getProduct().getPrice()*item.getQuantity();
        }
    }

    public CartDTO() {
    }

    public String getCartId() {
        return cartId;
    }

    public List<Item> getItems() {
        return items;
    }

    public float getSubtotal() {
        return subtotal;
    }
}
