package com.security6.Service.impl;

import com.security6.Entity.UserInfor;
import com.security6.Payload.UserLogin;
import com.security6.Repository.UserInforRepository;
import com.security6.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    private UserInforRepository repository;
    @Value("${jwt.secret}")
    private String jwtSecret;
    private HttpServletResponse response;
    private HttpServletRequest request;
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public UserServiceImpl(UserInforRepository repository, HttpServletResponse response, HttpServletRequest request) {
        this.repository = repository;
        this.response = response;
        this.request = request;
    }

    public String generateJwtToken(String username, String password) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("password", password);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    @Override
    public String addUser(UserInfor userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Thêm user thành công!";
    }

    public boolean hasAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }
    @Override
    public String Login(UserLogin userLogin) {
        String authorizationHeader = request.getHeader("Authorization");
        System.out.println("Authorization: "+authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token= authorizationHeader.substring(7);
            String username= Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            String password = (String) claims.get("password");
            if(repository.existsUserInforByEmail(username)){
                if (passwordEncoder.matches(password,repository.findByEmail(username).getPassword())){
                    response.addHeader("Authorization", "Bearer " + generateJwtToken(userLogin.getUsername(),userLogin.getPassword()));
                    return "token chuẩn";
                }
            }
        }
        if(repository.existsUserInforByEmail(userLogin.getUsername())){
            if (passwordEncoder.matches(userLogin.getPassword(),repository.findByEmail(userLogin.getUsername()).getPassword())){
                response.addHeader("Authorization", "Bearer " + generateJwtToken(userLogin.getUsername(),userLogin.getPassword()));
                return "Bearer " +generateJwtToken(userLogin.getUsername(),userLogin.getPassword());
            }
            return "Sai tài khoản hoặc mật khẩu";
        }
        return "Sai tài khoản hoặc mật khẩu";
    }
}
