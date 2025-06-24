package com.example.lemall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication

//等价
//@Configuration            我是配置类（可以定义 Bean）
//@EnableAutoConfiguration  我能自动配置 Spring Boot 需要的组件
//@ComponentScan            我能扫描同包及子包里的所有注解类
public class LemallApplication {
	public static void main(String[] args) {
        SpringApplication.run(LemallApplication.class, args);
    }

}

