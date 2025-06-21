package com.example.lemall.controller;

import com.example.lemall.entity.CartItem;
import com.example.lemall.entity.Product;
import com.example.lemall.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private ProductRepository productRepository;

    // 添加商品到购物车
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(item.getQuantity() + 1);
                    return "添加成功 (已存在 +1)";
                }
            }
            // 不存在则新增
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setImageUrl(product.getImageUrl());
            item.setQuantity(1);
            cart.add(item);
            return "添加成功 (新商品)";
        }
        return "商品不存在";
    }

    // 获取购物车列表
    @GetMapping("/list")
    public List<CartItem> getCart(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) return new ArrayList<>();
        return cart;
    }

    // 删除商品
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        return "删除成功";
    }

    // 修改数量
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProductId().equals(productId)) {
                    item.setQuantity(quantity);
                    break;
                }
            }
        }
        return "更新成功";
    }

    //清空购物车
    // 清空购物车
    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart");
        return "已清空";
    }

}
