package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;

public interface UserService {

    boolean login(String username, String password, String clientType) throws CouponSystemException;
}
