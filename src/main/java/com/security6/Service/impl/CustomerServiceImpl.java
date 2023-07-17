package com.security6.Service.impl;

import com.security6.Entity.Customer;
import com.security6.Payload.CustomerDTO;
import com.security6.Repository.CustomerRepository;
import com.security6.Service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    private ModelMapper modelMapper;

    public CustomerServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Customer> GetAllCustomer() {
        List<Customer> customers=repository.findAll();
        System.out.println("Danh sách customer"+customers);
        return repository.findAll();
    }

    @Override
    public String AddCustomer(CustomerDTO customerDTO) {
        Customer customer=modelMapper.map(customerDTO,Customer.class);
        if (customer == null){
            return "Không có dữ liệu";
        }
        if (repository.existsByEmail(customer.getEmail())){
            return "Email khách hàng đã sử dụng";
        }
        repository.save(customer);
        System.out.println(customer.toString());
        return customer.toString();
    }
}
