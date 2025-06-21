package com.example.lemall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;      // 外键，对应订单
    private Long productId;    // 商品 ID
    private String productName;
    private String productImage;
    private Double unitPrice;
    private Integer quantity;
}
