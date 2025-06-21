// ✅✅✅ 1. 添加实体类 CartItem.java（放 src/main/java/com/example/lemall/entity/）

package com.example.lemall.entity;

import lombok.Data;

@Data
public class CartItem {
    private Long productId;
    private String name;
    private double price;
    private String imageUrl;
    private int quantity;
}