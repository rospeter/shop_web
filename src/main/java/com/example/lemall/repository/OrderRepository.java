package com.example.lemall.repository;

import com.example.lemall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 你可以根据需要添加查询方法，比如查某用户的订单等
}
