package com.security6.Service;

import com.security6.Entity.UserInfor;
import com.security6.Repository.UserInforRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserInforDetailService implements UserDetailsService {
    private UserInforRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfor> userInfo = userInfoRepository.findByName(username);
        return null;
    }
}