package com.example.demo.service.customer;

import com.example.demo.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getCustomers();
    Customer getCustomer(long id);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(long id, Customer customer);
    void deleteCustomer(long id);
}

