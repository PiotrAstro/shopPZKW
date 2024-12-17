package com.example.shoppzkw;

import com.example.shoppzkw.model.Category;
import com.example.shoppzkw.model.ProductsRepository;
import com.example.shoppzkw.model.CategoriesRepository;
import com.example.shoppzkw.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository products;
    @Autowired
    private CategoriesRepository categories;


//    public ProductService() {
//        products = new ArrayList<>();
//    }

    public void seed() {
        categories.deleteAll();

        Category owoce = Category.builder().name("Owoce").code("K1").build();
        categories.save(owoce);
        Category warzywa = Category.builder().name("Warzywa").code("K2").build();
        categories.save(warzywa);

        products.save(
            Product.builder()
                .name("Pomidor")
                .category(owoce)
                .price(4.2)
                .weight(4.1)
                .build()
        );
        products.save(
            Product.builder()
                .name("Ogórek")
                .category(warzywa)
                .price(5.2)
                .weight(5.1)
                .build()
        );
        products.save(
            Product.builder()
                .name("Marchew")
                .category(warzywa)
                .price(3.2)
                .weight(3.1)
                .build()
        );
        products.save(
            Product.builder()
                .name("Ziemniak")
                .category(warzywa)
                .price(2.2)
                .weight(2.1)
                .build()
        );
        products.save(
            Product.builder()
                .name("Cebula")
                .category(warzywa)
                .price(1.2)
                .weight(1.1)
                .build()
        );
    }

    public List<Product> getAllProducts() {
        return products.findAll(Sort.by(Sort.Direction.ASC, "ProductId"));
    }

//    public void updateProduct(Product product) {
//        products.set(getProductIndex(product.getId()), product);
//    }

    public void addProduct(Product product) {
        products.save(product);
    }

    public Optional<Product> getProduct(long id) {
        return products.findById(id);
    }

    public boolean removeProduct(long id) {
        if (!hasProduct(id)) {
            return false;
        }
        products.deleteById(id);
        return true;
    }

    public boolean hasProduct(long id) {
        return products.existsById(id);
    }

    // ----------------- Category -----------------

    public List<Category> getAllCategories() {
        return categories.findAll(Sort.by(Sort.Direction.ASC, "CategoryId"));
    }

    public void addCategory(Category category) {
        categories.save(category);
    }

    public Optional<Category> getCategory(long id) {
        return categories.findById(id);
    }

    public boolean removeCategory(long id) {
        if (!hasCategory(id)) {
            return false;
        }
        categories.deleteById(id);
        return true;
    }

    public boolean hasCategory(long id) {
        return categories.existsById(id);
    }


}
