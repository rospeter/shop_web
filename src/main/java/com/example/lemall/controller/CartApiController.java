package com.example.lemall.controller;

import com.example.lemall.entity.CartItem;
import com.example.lemall.entity.Product;
import com.example.lemall.entity.User;
import com.example.lemall.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartApiController {

    @Autowired
    private ProductRepository productRepository;

    // 添加商品到购物车
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 没登录，返回 401，告诉前端要登录
            return ResponseEntity.status(401).body(Map.of("error", "请先登录再添加购物车"));
        }

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
                    return ResponseEntity.ok(Map.of("message", "添加成功 (已存在 +1)"));
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
            return ResponseEntity.ok(Map.of("message", "添加成功 (新商品)"));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "商品不存在"));
    }

    // 获取购物车列表
    @GetMapping("/list")
    public ResponseEntity<?> getCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "请先登录查看购物车"));
        }

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) return ResponseEntity.ok(new ArrayList<>());
        return ResponseEntity.ok(cart);
    }

    // 删除商品
    @PostMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long productId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "请先登录操作购物车"));
        }

        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getProductId().equals(productId));
        }
        return ResponseEntity.ok(Map.of("message", "删除成功"));
    }

    // 修改数量
    @PostMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "请先登录操作购物车"));
        }

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
        return ResponseEntity.ok(Map.of("message", "更新成功"));
    }

    // 清空购物车
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "请先登录操作购物车"));
        }

        session.removeAttribute("cart");
        return ResponseEntity.ok(Map.of("message", "已清空"));
    }
}
