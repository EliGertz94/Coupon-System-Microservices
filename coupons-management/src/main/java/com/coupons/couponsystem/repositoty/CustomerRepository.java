package com.coupons.couponsystem.repositoty;

import com.coupons.couponsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {




   Optional<Customer> findByUserId(Long userId);

//    List<Customer> findAllByCoupon_id(long coupon_id);
//


}
