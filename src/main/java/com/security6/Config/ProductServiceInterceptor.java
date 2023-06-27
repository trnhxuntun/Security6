package com.security6.Config;

import com.security6.Repository.UserInforRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Component
public class ProductServiceInterceptor implements HandlerInterceptor {

    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    @Value("${jwt.secret}")
    private String jwtSecret;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    private boolean checktoken(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
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
                    return true;
                }
            }
        }
        return false;
    }
    @Autowired
    private UserInforRepository repository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(checktoken(request)){
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Không có quyền truy cập. Hãy đăng nhập để tiếp tục.");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

    }
}