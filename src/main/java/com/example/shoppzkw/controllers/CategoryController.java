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
@RequestMapping("/Category")
public class CategoryController {
    private final ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public String categories(Model model) {
        model.addAttribute("categories", productService.getAllCategories());
        return "/Category/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "/Category/add";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute Category category, Errors errors, Model model) {
        productService.addCategory(category, errors);
        if (errors.hasErrors()) {
            model.addAttribute("category", category);
            return "/Category/add";
        }
        return "redirect:/Category/" + category.getCategoryId();
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") int id) {
        boolean success = productService.removeCategory(id);
        if (!success) {
            return "redirect:/Category/removeError";
        }
        return "redirect:/Category/";
    }

    @GetMapping("/removeError")
    public String removeError(Model model) {
        model.addAttribute("message", "Category with this id does not exist");
        return "message";
    }

    @GetMapping("/{categoryId}")
    public String details(Model model, @PathVariable("categoryId") int id) {
        Optional<Category> category = productService.getCategory(id);
        if (category.isEmpty()) {
            return "redirect:/Category/detailsError";
        }
        model.addAttribute("category", category.get());
        return "/Category/details";
    }

    @GetMapping("/detailsError")
    public String detailsError(Model model) {
        model.addAttribute("message", "Category with this id does not exist");
        return "message";
    }

    @GetMapping("/{categoryId}/edit")
    public String edit(Model model, @PathVariable("categoryId") int id) {
        Optional<Category> category = productService.getCategory(id);
        if (category.isEmpty()) {
            return "redirect:/Category/editError";
        }
        model.addAttribute("category", category.get());
        return "/Category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute Category category, Errors errors, Model model) {
        if (!productService.hasCategory(category.getCategoryId())) {
            return "redirect:/Category/editError";
        }

        productService.editCategory(category, errors);
        if (errors.hasErrors()) {
            model.addAttribute("category", category);
            return "/Category/edit";
        }
        return "redirect:/Category/" + category.getCategoryId();
    }

    @GetMapping("/editError")
    public String editError(Model model) {
        model.addAttribute("message", "Category with this id does not exist");
        return "message";
    }
}
