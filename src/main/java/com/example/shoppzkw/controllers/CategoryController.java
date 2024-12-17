package com.example.shoppzkw.controllers;

import com.example.shoppzkw.ProductService;
import com.example.shoppzkw.model.Category;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@Controller
@RequestMapping("/Products")
public class CategoryController {
    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String categories(Model model) {
        model.addAttribute("categories", productService.getAllCategories());
        return "Category/index";
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());
        return "/Category/add";
    }

    @PostMapping("/add")
    public String addCategory(@Valid Category category, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "/Category/add";
        }
        productService.addCategory(category);
        return "redirect:/Category/" + category.getCategoryId();
    }

    @GetMapping("/{categoryId}")
    public String categoryDetails(Model model, @PathVariable("categoryId") int id) {
        Optional<Category> category = productService.getCategory(id);
        if (category.isEmpty()) {
            return "redirect:/Category/detailsError";
        }
        model.addAttribute("category", category.get());
        return "/Category/details";
    }

    @GetMapping("/detailsError")
    public String categoryDetailsError(Model model) {
        model.addAttribute("message", "Category with this id does not exist");
        return "message";
    }

    @GetMapping("/remove")
    public String removeCategory(@RequestParam("id") int id) {
        boolean success = productService.removeCategory(id);
        if (!success) {
            return "redirect:/Category/removeError";
        }
        return "redirect:/Category";
    }

    @GetMapping("/removeError")
    public String removeCategoryError(Model model) {
        model.addAttribute("message", "Category with this id does not exist");
        return "message";
    }
}
