package com.example.lemall.controller;

import com.example.lemall.entity.User;
import com.example.lemall.entity.Order;
import com.example.lemall.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserPageController {

    @Autowired
    private OrderRepository orderRepository;

    // 用户中心首页
    @GetMapping("/center")
    public String userCenter(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("user", user);
        return "user-center"; // templates/user-center.html
    }

    // 我的订单
    @GetMapping("/orders")
    public String userOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Order> orders = orderRepository.findByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "user-orders"; // templates/user-orders.html
    }

    // 退出登录
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
