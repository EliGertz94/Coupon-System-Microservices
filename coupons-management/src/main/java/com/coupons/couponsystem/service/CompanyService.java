package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;

import java.util.List;

public interface CompanyService {

//     boolean logIn(String email, String password) throws CouponSystemException;

    Coupon addCoupon(Coupon coupon) throws CouponSystemException;

    Coupon updateCoupon(Coupon coupon) throws CouponSystemException;

    void deleteCoupon(long couponId) throws CouponSystemException;

    List<Coupon> getAllCompanyCoupons();

    List<Coupon> getAllCompanyCouponsByCategory(String category);

    List<Coupon> getAllCompanyCouponsByPrice(double maxPrice);

    Company getCompanyDetails() throws CouponSystemException;

}
