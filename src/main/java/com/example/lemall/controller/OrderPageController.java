package com.example.lemall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/order")
public class OrderPageController {

    @GetMapping("/pay")
    public String payPage(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "pay"; // 映射到 resources/templates/pay.html
    }

    @GetMapping("/pay-code")
    public String payCodePage(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "pay-code"; // 映射到 resources/templates/pay-code.html
    }
}
