package com.example.lemall.controller;

import com.example.lemall.entity.Order;
import com.example.lemall.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderPageController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/pay")
    public String payPage(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "pay"; // resources/templates/pay.html
    }

    @GetMapping("/pay-code")
    public String payCodePage(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "pay-code"; // resources/templates/pay-code.html
    }

    // 新增：模拟支付确认处理
    @PostMapping("/confirm/{orderId}")
    public String confirmPayment(@PathVariable Long orderId, Model model) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            model.addAttribute("error", "订单不存在！");
            return "error";
        }

        order.setStatus("已支付");
        orderRepository.save(order);

        model.addAttribute("order", order);
        return "pay-success"; // 支付成功页面
    }
}
