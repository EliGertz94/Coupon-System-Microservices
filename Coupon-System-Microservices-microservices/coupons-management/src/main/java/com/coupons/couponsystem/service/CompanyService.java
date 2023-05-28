package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

//     boolean logIn(String email, String password) throws CouponSystemException;

    Coupon addCoupon(Long companyId, Coupon coupon, MultipartFile file) throws CouponSystemException, IOException;

    Coupon updateCoupon(Coupon coupon,long userId) throws CouponSystemException;

    void deleteCoupon(long couponId,long companyId) throws CouponSystemException;

    List<Coupon> getAllCompanyCoupons(long companyId) throws CouponSystemException;

    List<Coupon> getAllCompanyCouponsByCategory(String category,long companyId);

    List<Coupon> getAllCompanyCouponsByPrice(double maxPrice,long companyId);

    Company getCompanyDetails(long companyId) throws CouponSystemException;

}
