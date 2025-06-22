package com.example.lemall.controller;

import com.example.lemall.entity.CartItem;
import com.example.lemall.entity.Order;
import com.example.lemall.entity.OrderItem;
import com.example.lemall.entity.User;
import com.example.lemall.repository.OrderItemRepository;
import com.example.lemall.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/create")
    public Response createOrder(HttpSession session) {
        // 1. 获取登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new Response(false, "用户未登录");
        }

        // 2. 获取购物车
        @SuppressWarnings("unchecked")
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return new Response(false, "购物车为空");
        }

        // 3. 创建订单对象
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("待支付");

        // 4. 计算总价
        double total = 0;
        for (CartItem item : cart) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotalPrice(total);

        // 5. 保存订单，获取订单ID
        order = orderRepository.save(order);

        // 6. 保存订单项
        for (CartItem item : cart) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(item.getProductId());
            orderItem.setProductName(item.getName());
            orderItem.setProductImage(item.getImageUrl());
            orderItem.setUnitPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());

            orderItemRepository.save(orderItem);
        }

        // 7. 清空购物车
        session.removeAttribute("cart");

        // 8. 返回结果
        return new Response(true, "订单创建成功", order.getId());
    }
    // ===== 新增订单详情接口，用于根据订单ID查询订单信息 =====
    @GetMapping("/detail")
    public OrderDetailResponse getOrderDetail(@RequestParam Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new RuntimeException("订单不存在"); // 简单异常处理
        }

        // 查询订单项列表
        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);
        order.setItems(items);  // 设置订单中的订单项（transient 字段）

        return new OrderDetailResponse(order);
    }

    // 定义订单详情返回数据结构
    public static class OrderDetailResponse {
        private Long orderId;
        private Double total;
        private List<OrderItem> items;

        public OrderDetailResponse(Order order) {
            this.orderId = order.getId();
            this.total = order.getTotalPrice();
            this.items = order.getItems();
        }

        public Long getOrderId() {
            return orderId;
        }

        public Double getTotal() {
            return total;
        }

        public List<OrderItem> getItems() {
            return items;
        }
    }

    // 简单响应类
    public static class Response {
        private boolean success;
        private String message;
        private Long orderId;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public Response(boolean success, String message, Long orderId) {
            this.success = success;
            this.message = message;
            this.orderId = orderId;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Long getOrderId() {
            return orderId;
        }
    }
}
