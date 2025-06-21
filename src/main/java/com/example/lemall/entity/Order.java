package com.example.lemall.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders") // 注意：数据库里我们将创建 orders 表
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private LocalDateTime orderTime;

    private Double totalPrice;

    private String status;

    // 不映射到数据库，仅用于封装前端展示
    @Transient
    private List<OrderItem> items;
}
