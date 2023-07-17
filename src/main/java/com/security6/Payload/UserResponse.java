package com.security6.Payload;

import com.security6.Entity.Product;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private String name;
    private String email;
    private Set<Product> products;
}
