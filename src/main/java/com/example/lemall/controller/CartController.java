package com.example.lemall.controller;

import com.example.lemall.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {

    @GetMapping("/cart")
    public String cartPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 没登录，跳登录页面
            return "redirect:/login";
        }
        // 已登录，正常返回购物车页面模板
        return "cart";  // 这里对应 templates/cart.html 页面
    }
}
