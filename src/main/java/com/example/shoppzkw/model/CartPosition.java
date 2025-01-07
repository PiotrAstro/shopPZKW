package com.example.shoppzkw.model;

import lombok.Data;

@Data
public class CartPosition {
    private long productId;
    private String name;
    private double price;
    private double weight;
    private int quantity;

    public CartPosition(Product product, int quantity) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.weight = product.getWeight();
        this.quantity = quantity;
    }
}