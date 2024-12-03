package com.example.shoppzkw;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Product> products;

    public ProductService() {
        products = new ArrayList<>();
    }

    public void seed() {
        products.clear();
        products.add(new Product(1, "Pomidor", "Owoc", 4.2, 4.1));
        products.add(new Product(2, "Ogórek", "Warzywa", 5.2, 5.1));
        products.add(new Product(3, "Marchew", "Warzywa", 3.2, 3.1));
        products.add(new Product(4, "Ziemniak", "Warzywa", 2.2, 2.1));
        products.add(new Product(5, "Cebula", "Warzywa", 1.2, 1.1));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void updateProduct(Product product) {
        products.set(getProductIndex(product.getId()), product);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct(int id) {
        return products.get(getProductIndex(id));
    }

    public void removeProduct(int id) {
        products.remove(getProductIndex(id));
    }

    public boolean hasProduct(int id) {
        return getProductIndex(id) != -1;
    }

    private int getProductIndex(int id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
