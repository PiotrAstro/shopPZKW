package com.example.shoppzkw.controllers;

import com.example.shoppzkw.ProductService;
import com.example.shoppzkw.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/adm/Product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/adm/Product/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productService.getAllCategories());
        return "/adm/Product/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Product product, Errors errors, Model model) {
        productService.addProduct(product, errors);
        if (errors.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            return "/adm/Product/add";
        }
        return "redirect:/adm/Product/" + product.getProductId();
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") int id) {
        boolean success = productService.removeProduct(id);
        if (!success) {
            return "redirect:/adm/Product/removeError";
        }
        return "redirect:/adm/Product/";
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
            return "redirect:/adm/Product/detailsError";
        }
        model.addAttribute("product", product.get());
        return "/adm/Product/details";
    }

    @GetMapping("/detailsError")
    public String detailsError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    @GetMapping("/{productId}/edit")
    public String edit(Model model, @PathVariable("productId") int id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isEmpty()) {
            return "redirect:/adm/Product/editError";
        }
        model.addAttribute("product", product.get());
        model.addAttribute("categories", productService.getAllCategories());
        return "/adm/Product/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Product product, Errors errors, Model model) {
        if (!productService.hasProduct(product.getProductId())) {
            return "redirect:/adm/Product/editError";
        }

        productService.editProduct(product, errors);
        if (errors.hasErrors()) {
            model.addAttribute("product", product);
            model.addAttribute("categories", productService.getAllCategories());
            return "/adm/Product/edit";
        }
        return "redirect:/adm/Product/" + product.getProductId();
    }

    @GetMapping("/editError")
    public String editError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }
}
