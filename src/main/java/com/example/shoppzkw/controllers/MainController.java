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
        return "redirect:/Product/";
    }

    @GetMapping("/seed")
    public String seed() {
        productService.seed();
        return "redirect:/";
    }
}
