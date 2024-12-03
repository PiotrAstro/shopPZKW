package com.example.shoppzkw;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @GetMapping("/seed")
    public String seed() {
        productService.seed();
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        if (productService.hasProduct(product.getId())) {
            return "redirect:/addError";
        }
        productService.addProduct(product);
        return "redirect:/";
    }

    @GetMapping("/addError")
    public String addError(Model model) {
        model.addAttribute("message", "Product with this id already exists");
        return "message";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam("id") int id) {
        if (!productService.hasProduct(id)) {
            return "redirect:/removeError";
        }
        productService.removeProduct(id);
        return "redirect:/";
    }

    @GetMapping("/removeError")
    public String removeError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    @GetMapping("/{productId}/edit")
    public String edit(Model model, @PathVariable("productId") int id) {
        model.addAttribute("product", productService.getProduct(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product) {
        if (!productService.hasProduct(product.getId())) {
            return "redirect:/editError";
        }
        productService.updateProduct(product);
        return "redirect:/";
    }

    @GetMapping("/editError")
    public String editError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    @GetMapping("/{productId}")
    public String details(Model model, @PathVariable("productId") int id) {
        if (!productService.hasProduct(id)) {
            return "redirect:/detailsError";
        }
        model.addAttribute("product", productService.getProduct(id));
        return "details";
    }

    @GetMapping("/detailsError")
    public String detailsError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }
}
