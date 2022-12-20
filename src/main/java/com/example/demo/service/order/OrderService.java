package com.example.demo.service.order;

import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

//    public OrderServiceImpl(OrderRepository orderRepository);
    public List<Order> getOrders();
    public Order getOrder(long id) ;
    public Order createOrder(Order order) ;
    public Order updateOrder(long id, Order order) ;
    public void deleteOrder(long id) ;
}
