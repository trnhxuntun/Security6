package com.security6.Service.impl;

import com.security6.Entity.Product;
import com.security6.Entity.User;
import com.security6.Exception.ResourceNotFoundException;
import com.security6.Payload.UserLogin;
import com.security6.Payload.UserResponse;
import com.security6.Repository.ProductRepository;
import com.security6.Repository.UserRepository;
import com.security6.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    @Value("${jwt.secret}")
    private String jwtSecret;
    private final HttpServletResponse response;
    private final HttpServletRequest request;
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    private ModelMapper mapper;
    @Autowired
     private ProductRepository productRepository;
    public UserServiceImpl(UserRepository repository, HttpServletResponse response, HttpServletRequest request, ModelMapper mapper) {
        this.repository = repository;
        this.response = response;
        this.request = request;
        this.mapper = mapper;
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
    public String addUser(User userInfo) {
        if(repository.existsUserByEmail(userInfo.getEmail()))
            return "Email đã được đăng ký!!!";
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Thêm user "+userInfo.getName()+" thành công!!!";
    }
    @Override
    public String Login(UserLogin userLogin) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
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
            if(repository.existsUserByName(username)){
                    response.addHeader("Authorization", "Bearer " + generateJwtToken(userLogin.getUsername(),userLogin.getPassword()));
                    return "token chuẩn!!!";
            }
            }catch (Exception ex){
                System.out.println("Không có token hoặc token hết hạn");
                if(repository.existsUserByEmail(userLogin.getUsername())){
                    if (passwordEncoder.matches(userLogin.getPassword(),repository.findByEmail(userLogin.getUsername()).getPassword())){
                        response.addHeader("Authorization", "Bearer " + generateJwtToken(userLogin.getUsername(),userLogin.getPassword()));
                        return "Đăng nhập thành công!!!";
                    }
                    return "Sai tài khoản hoặc mật khẩu!!!";
                }

            }
        }
        return "Sai tài khoản hoặc mật khẩu!!!";
    }

    @Override
    public UserResponse addProduct(int id, Long idpro) {
        System.out.println(id+"||"+idpro);
        User user=repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(id) ));
        Product product=productRepository.findById(idpro).orElseThrow(() -> new ResourceNotFoundException("sản phẩm","id",String.valueOf(idpro)));
        user.getProducts().add(product);
        repository.save(user);
        UserResponse userResponse=mapper.map(user,UserResponse.class);
        return userResponse;
    }
}
