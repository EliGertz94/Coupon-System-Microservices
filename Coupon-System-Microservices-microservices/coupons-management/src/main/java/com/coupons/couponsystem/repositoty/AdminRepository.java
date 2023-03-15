package com.coupons.couponsystem.repositoty;

import com.coupons.couponsystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByEmail(String email);
    boolean existsByEmail(String email);


    Optional<Admin> findByEmailAndPassword(String email, String password);


}
