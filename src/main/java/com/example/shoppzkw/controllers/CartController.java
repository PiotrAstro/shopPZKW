package com.example.shoppzkw.controllers;

import com.example.shoppzkw.ProductService;
import com.example.shoppzkw.model.CartPosition;
import com.example.shoppzkw.model.Product;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Double.valueOf;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/index";
    }

    @PostMapping("/add")
    public String add(HttpServletResponse response, HttpServletRequest request, long id) {
        List<CartPosition> cart = getCartFromCookie(request);
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            boolean found = false;
            for (CartPosition cartPosition : cart) {
                if (cartPosition.getProductId() == id) {
                    cartPosition.setQuantity(cartPosition.getQuantity() + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                cart.add(new CartPosition(product.get(), 1));
            }
        } else {
            return "redirect:/cart/idError";
        }
        saveToCookie(response, cart);
        return "redirect:/cart/summary";
    }

    @PostMapping("/remove")
    public String remove(HttpServletResponse response, HttpServletRequest request, long id) {
        List<CartPosition> cart = getCartFromCookie(request);
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            boolean found = false;
            for (CartPosition cartPosition : cart) {
                if (cartPosition.getProductId() == id) {
                    if (cartPosition.getQuantity() > 1) {
                        cartPosition.setQuantity(cartPosition.getQuantity() - 1);
                    } else {
                        cart.remove(cartPosition);
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                return "redirect:/cart/idError";
            }
        } else {
            return "redirect:/cart/idError";
        }
        saveToCookie(response, cart);
        return "redirect:/cart/summary";
    }

    @PostMapping("/clear")
    public String clear(HttpServletResponse response) {
        clearCartCookie(response);
        return "redirect:/cart/summary";
    }

    @GetMapping("/summary")
    public String summary(HttpServletRequest request, Model model) {
        List<CartPosition> cart = getCartFromCookie(request);
        double totalPrice = 0.0;
        for (CartPosition cartPosition : cart) {
            totalPrice += cartPosition.getPrice() * cartPosition.getQuantity();
        }
        model.addAttribute("products", cart);
        DecimalFormat df = new DecimalFormat("#.00");
        model.addAttribute("totalPrice", df.format(totalPrice));
        return "summary";
    }

    @GetMapping("/idError")
    public String idError(Model model) {
        model.addAttribute("message", "Product with this id does not exist");
        return "message";
    }

    private List<CartPosition> getCartFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        List<CartPosition> cart = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cart")) {
                    String[] cartItems = cookie.getValue().split("_");
                    for (String cartItem : cartItems) {
                        String[] item = cartItem.split("#");
                        if (item.length == 2) {
                            try {
                                long id = Long.parseLong(item[0]);
                                int quantity = Integer.parseInt(item[1]);
                                if (quantity > 0) {
                                    Optional<Product> product = productService.getProduct(id);
                                    product.ifPresent(value -> cart.add(new CartPosition(value, quantity)));
                                }
                            } catch (NumberFormatException e) {

                            }
                        }
                    }
                }
            }
        }
        return cart;
    }

    private void saveToCookie(HttpServletResponse response, List<CartPosition> cart) {
        String cookieValue = cart.stream().map(cartPosition -> cartPosition.getProductId() + "#" + cartPosition.getQuantity()).reduce((s, s2) -> s + "_" + s2).orElse("");
        Cookie cookie = new Cookie("cart", cookieValue);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    private void clearCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("cart", "");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
