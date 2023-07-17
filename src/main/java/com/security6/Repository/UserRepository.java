package com.security6.Repository;

import com.security6.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByName(String username);
    boolean existsUserByName(String name);

    boolean existsUserByEmail(String email);
    User findByEmail(String email);
}
