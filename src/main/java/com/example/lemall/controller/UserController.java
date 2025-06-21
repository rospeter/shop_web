package com.example.lemall.controller;

import com.example.lemall.entity.User;
import com.example.lemall.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public Response register(@RequestBody User user, HttpSession session) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return new Response(false, "用户名已存在，不能注册");
        }
        user.setRole("USER");
        userRepository.save(user);

        // 自动登录
        session.setAttribute("user", user);

        return new Response(true, "注册成功，自动登录！");
    }

    static class Response {
        private boolean success;
        private String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
