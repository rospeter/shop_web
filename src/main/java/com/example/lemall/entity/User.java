package com.example.lemall.entity;

import lombok.Data;
import jakarta.persistence.*;
//自动生成 getXxx() / setXxx() / toString() / equals() / hashCode()
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String role;  // 新增：角色，字符串，"ADMIN" 或 "USER"
}
