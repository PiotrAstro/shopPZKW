package com.example.shoppzkw;

import java.util.Objects;

public class Product {
    private static int idCounter = 1;

    private int id;
    private String name;
    private String category;
    private double price;
    private double weight;

    public Product() {
        this.id = idCounter;
        idCounter++;
    }

    public Product(String name, String category, double price, double weight) {
        this();
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
