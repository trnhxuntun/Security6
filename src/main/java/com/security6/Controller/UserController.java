package com.security6.Controller;

import com.security6.Entity.User;
import com.security6.Payload.UserLogin;
import com.security6.Payload.UserResponse;
import com.security6.Repository.UserRepository;
import com.security6.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:9000")
public class UserController {
    @Autowired
    UserRepository repository;
    private final UserService userService;

    @PostMapping("/new")
    public String addUser(@RequestBody() User userInfo) {
        userInfo.setRoles("ROLE_USER");
        return userService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String Login(@RequestBody UserLogin userLogin) {
        return userService.Login(userLogin);
    }

    @PostMapping("/{id}/product/{idpro}")
    public ResponseEntity<UserResponse> addproduct(@PathVariable int id, @PathVariable Long idpro){
        return new ResponseEntity<>(userService.addProduct(id,idpro), HttpStatus.CREATED);
    }
}
