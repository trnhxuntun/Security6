package com.security6.Repository;

import com.security6.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String Email);
}
