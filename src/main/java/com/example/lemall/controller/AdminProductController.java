package com.example.lemall.controller;

import com.example.lemall.entity.Product;
import com.example.lemall.entity.User;
import com.example.lemall.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")  // 所有/admin开头
public class AdminProductController {

    @Autowired
    private ProductRepository productRepository;

    // 权限检查方法（私有）
    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user != null && "ADMIN".equals(user.getRole());
    }

    // 1. 商品列表页
    @GetMapping("/products")
    public String listProducts(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";  // 非管理员跳登录
        }
        List<Product> productList = productRepository.findAll();
        model.addAttribute("products", productList);
        return "admin/product_list";
    }

    // 2. 新增商品页
    @GetMapping("/product/new")
    public String newProduct(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        model.addAttribute("product", new Product());
        return "admin/product_form";
    }

    // 3. 编辑商品页
    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            return "admin/product_form";
        } else {
            return "redirect:/admin/products";
        }
    }

    // 4. 保存商品（新增或编辑）
    @PostMapping("/product/save")
    public String saveProduct(@ModelAttribute Product product, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        productRepository.save(product);
        return "redirect:/admin/products";
    }

    // 5. 删除商品
    @PostMapping("/product/delete")
    public String deleteProduct(@RequestParam Long id, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/login";
        }
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
