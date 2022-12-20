package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.service.customer.Customer_Service;
import com.example.demo.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan("com.example.demo.model.*")
@EnableJpaRepositories("com.example.demo.repository")
@EntityScan(basePackageClasses = {Customer.class, Order.class})
public class Main {
//    @Autowired
//    private Customer_Service customerService;
//
//    @Autowired
//    private OrderService orderService;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

