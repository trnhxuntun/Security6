package com.security6.Controller;

import com.security6.Entity.Customer;
import com.security6.Entity.UserInfor;
import com.security6.Service.CustomerService;
import com.security6.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping("/customer/all")
    public ResponseEntity<List<Customer>> allCustomer(){
        return new ResponseEntity<>(customerService.GetAllCustomer(), HttpStatus.OK);
    }

}


