package com.example.lemall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 首页路径（根路径跳转）
    @GetMapping("/")
    public String root() {
        return "redirect:/home"; // 重定向到 /home
    }

    // 显示首页模板页面（templates/home.html）
    @GetMapping("/home")
    public String home() {
        return "home"; // ❌不要写 "home.html"，直接写 "home"
    }
}
