package com.example.shoppzkw.controllers;

import com.example.shoppzkw.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final ProductService productService;

    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/cart/";
    }

    @GetMapping("/adm/seed")
    public String seed() {
        productService.seed();
        return "redirect:/adm/";
    }

    @GetMapping("/adm/")
    public String admin() {
        productService.seed();
        return "redirect:/adm/Product/";
    }
}
