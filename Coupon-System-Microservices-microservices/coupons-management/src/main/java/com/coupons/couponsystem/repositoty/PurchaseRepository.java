package com.coupons.couponsystem.repositoty;

import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

   // List<Purchase> findAllByCustomers_idAndCategory(long customers_id, Category category);

   List<Purchase> findAllByCustomer_idAndTotalPriceLessThanEqual(long customer_id, double totalPrice);
   List<Purchase> findByCustomer_idOrderByIdDesc(long customers_id);
//   List<Purchase> findAllByPurchaseDateAfter(LocalDateTime purchaseDate);
   Optional<List<Purchase>> findAllByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);


}