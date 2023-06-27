package com.security6.Repository;

import com.security6.Entity.UserInfor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInforRepository extends JpaRepository<UserInfor,Integer> {
    Optional<UserInfor> findByName(String username);
    boolean existsUserInforByEmail(String email);
    UserInfor findByEmail(String email);
}
