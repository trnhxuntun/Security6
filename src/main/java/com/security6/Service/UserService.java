package com.security6.Service;

import com.security6.Entity.UserInfor;
import com.security6.Payload.UserLogin;
import com.security6.Repository.UserInforRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

public interface UserService {
    public String addUser(UserInfor userInfo);
    public String Login(UserLogin userLogin);
}
