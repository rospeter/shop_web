package com.example.lemall.controller;

import com.example.lemall.entity.Product;
import com.example.lemall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//
//         | 注解              | 意义                               |
//        | --------------- | -------------------------------- |
//        | @Controller     | 返回的是页面名（模板）                      |
//        | @RestController | 返回的是 JSON 数据（自动带上 @ResponseBody） |
//        | @ResponseBody   | 把返回对象变成 JSON 响应体返回               |

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductApiController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
