package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;

import java.util.List;

public interface CustomerService {


//    boolean logIn(String email, String password) throws CouponSystemException;

    String makePurchase(List<Long> couponIdList) throws CouponSystemException;

    List<Purchase> getCustomerPurchases() throws CouponSystemException;

    List<Purchase> getCustomerPurchases(double maxPrice) throws CouponSystemException;

//    List<Coupon> getCustomerPurchases(Category category);

    Customer getCustomerDetails() throws CouponSystemException;




}
