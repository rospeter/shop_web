package com.example.lemall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    // GET请求，访问 /register 时返回注册页面 register.html
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";  // 返回 templates/register.html 页面（不要加 .html）
    }
}
