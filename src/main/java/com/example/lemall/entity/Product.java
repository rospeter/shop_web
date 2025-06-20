package com.example.lemall.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")  // 明确表名，避免未来类名改动带来问题
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增主键
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;  // 商品名称

    @Column(nullable = false)
    private double price; // 商品价格

    @Column(name = "image_url", length = 255)
    private String imageUrl; // 商品图片地址

    // ===== Getter 和 Setter =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
