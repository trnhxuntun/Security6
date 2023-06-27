package com.security6.Service.impl;

import com.security6.Entity.Customer;
import com.security6.Repository.CustomerRepository;
import com.security6.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Override
    public List<Customer> GetAllCustomer() {
        List<Customer> customers=repository.findAll();
        System.out.println("Danh sách customer"+customers);
        return repository.findAll();
    }
}
