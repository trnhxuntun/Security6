//package com.security6.Config;
//
//import org.springframework.aop.interceptor.SimpleTraceInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class AppConfig implements WebMvcConfigurer {
//
//    @Autowired
//    ProductServiceInterceptor productServiceInterceptor;
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(productServiceInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login","/user/new","/admin/customer/all","/admin/customer/add");
//    }
//}