package com.example.shoppzkw;

import java.util.Objects;

public class Product {
    private int id;
    private String name;
    private String category;
    private double price;
    private double weight;

    public Product() {
        this.id = 1;
    }

    public Product(int id, String name, String category, double price, double weight) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }
}
