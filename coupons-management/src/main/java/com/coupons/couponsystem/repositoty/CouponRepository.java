package com.coupons.couponsystem.repositoty;

import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByTitleAndCompanyId(String title,long companyId);
    boolean existsByCompanyId(long companyId);

    List<Coupon> findAllByCompany_id(long company_id);

    List<Coupon> findAllByCompanyId(long id);

//    List<Coupon> findAllByCustomers_id(long customers_id);
//    boolean existsByCustomers_idAndId(long customers_id, long id);
//    List<Coupon> findByIdAndCustomers_id(long id,long customers_id);

    List<Coupon> findAllByCompany_idAndCategory(long CompanyId,Category category);
    //LessThanEqual

    //max price
    List<Coupon> findAllByCompany_idAndPriceLessThanEqual(long CompanyId,double price);

//    List<Coupon> findAllByCustomers_idAndCategory(long customers_id,Category category);
//
//    List<Coupon> findAllByCustomers_idAndPriceLessThanEqual(long customer_id,double price);

   // long deleteAllByEndDateBefore(LocalDateTime endDate);
//    void deleteByEndDateBefore(LocalDateTime endDate);

    List<Coupon> findAllByEndDateBefore(LocalDateTime endDate);
}

