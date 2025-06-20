package com.example.lemall.controller;

import com.example.lemall.entity.User;
import com.example.lemall.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "用户名已存在，不能注册";
        }

        user.setRole("USER");  // 这里给新注册用户赋默认角色 USER
        userRepository.save(user);
        return "注册成功！";
    }

}

