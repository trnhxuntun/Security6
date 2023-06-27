package com.security6.Controller;

import com.security6.Entity.UserInfor;
import com.security6.Payload.UserLogin;
import com.security6.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/new")
    public String addUser(@RequestBody UserInfor userInfo) {
        return userService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String Login(@RequestBody UserLogin userLogin) {
        return userService.Login(userLogin);
    }
}
