package com.security6.Service;

import com.security6.Entity.Customer;
import com.security6.Payload.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<Customer> GetAllCustomer();

    String AddCustomer(CustomerDTO customerDTO);
}
