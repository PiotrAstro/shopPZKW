package com.example.shoppzkw.controllers;

import com.example.shoppzkw.ProductService;
import com.example.shoppzkw.model.Category;
import com.example.shoppzkw.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/Product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/Product/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        return "/Product/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            return "/Product/add";
        }
        productService.addProduct(product);
        return "redirect:/Product/" + product.getProductId();
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") int id) {
        boolean success = productService.removeProduct(id);
        if (!success) {
            return "redirect:/Product/removeError";
        }
        return "redirect:/Product";
    }

    @GetMapping("/removeError")
    public String removeError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    @GetMapping("/{productId}")
    public String details(Model model, @PathVariable("productId") int id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return "redirect:/Product/detailsError";
        }
        model.addAttribute("product", product.get());
        return "/Product/details";
    }

    @GetMapping("/detailsError")
    public String detailsError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    //    @GetMapping("/{productId}/edit")
//    public String edit(Model model, @PathVariable("productId") int id) {
//        model.addAttribute("product", productService.getProduct(id));
//        return "edit";
//    }

//    @PostMapping("/edit")
//    public String edit(@ModelAttribute Product product) {
//        if (!productService.hasProduct(product.getId())) {
//            return "redirect:/editError";
//        }
//        productService.updateProduct(product);
//        return "redirect:/";
//    }

//    @GetMapping("/editError")
//    public String editError(Model model) {
//        model.addAttribute("message", "Product with this id does not exist");
//        return "message";
//    }
}
