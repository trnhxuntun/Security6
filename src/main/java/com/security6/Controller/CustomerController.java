package com.security6.Controller;

import com.security6.Entity.Customer;
import com.security6.Payload.CustomerDTO;
import com.security6.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
@CrossOrigin("http://localhost:9000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/customer/all")
    public ResponseEntity<List<Customer>> allCustomer(){
        return new ResponseEntity<>(customerService.GetAllCustomer(), HttpStatus.OK);
    }
    @PostMapping("/customer/add")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<>(customerService.AddCustomer(customerDTO),HttpStatus.CREATED);
    }
}


