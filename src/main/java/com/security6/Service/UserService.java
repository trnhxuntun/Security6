package com.security6.Service;

import com.security6.Entity.User;
import com.security6.Payload.UserLogin;
import com.security6.Payload.UserResponse;

public interface UserService {
    public String addUser(User userInfo);
    public String Login(UserLogin userLogin);

    public UserResponse addProduct(int id,Long idpro);
}
