package com.example.lemall.repository;

import com.example.lemall.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 加上这个方法用于查找当前用户的所有订单
    List<Order> findByUserId(Long userId);
}
