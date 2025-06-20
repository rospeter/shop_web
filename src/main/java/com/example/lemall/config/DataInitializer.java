package com.example.lemall.config;

import com.example.lemall.entity.Product;
import com.example.lemall.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Product p1 = new Product();
                p1.setName("无线耳机");
                p1.setPrice(199.00);
                p1.setImageUrl("https://via.placeholder.com/300x200?text=无线耳机");

                Product p2 = new Product();
                p2.setName("蓝牙音箱");
                p2.setPrice(299.00);
                p2.setImageUrl("https://via.placeholder.com/300x200?text=蓝牙音箱");

                Product p3 = new Product();
                p3.setName("智能手表");
                p3.setPrice(399.00);
                p3.setImageUrl("https://via.placeholder.com/300x200?text=智能手表");

                // 新增三个商品
                Product p4 = new Product();
                p4.setName("便携充电宝");
                p4.setPrice(149.00);
                p4.setImageUrl("https://via.placeholder.com/300x200?text=充电宝");

                Product p5 = new Product();
                p5.setName("高清摄像头");
                p5.setPrice(499.00);
                p5.setImageUrl("https://via.placeholder.com/300x200?text=摄像头");

                Product p6 = new Product();
                p6.setName("无线键盘");
                p6.setPrice(259.00);
                p6.setImageUrl("https://via.placeholder.com/300x200?text=无线键盘");

                productRepository.save(p1);
                productRepository.save(p2);
                productRepository.save(p3);
                productRepository.save(p4);
                productRepository.save(p5);
                productRepository.save(p6);

                System.out.println("已插入初始商品数据！");
            }
        };
    }
}
