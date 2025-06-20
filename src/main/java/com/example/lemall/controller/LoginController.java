package com.example.lemall.controller;

import com.example.lemall.entity.User;
import com.example.lemall.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // 返回模板 login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                session.setAttribute("user", user);  // 把用户放进session
                return "redirect:/home"; // 登录成功跳首页
            }
        }
        return "redirect:/login?error=true"; // 登录失败返回登录页
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 注销清空session
        return "redirect:/home?logout=true"; // 注销后跳首页
    }



}
