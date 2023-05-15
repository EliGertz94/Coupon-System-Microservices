package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.clientLogIn.LoginManager;
import com.coupons.couponsystem.model.ImageData;
import com.coupons.couponsystem.repositoty.CouponRepository;
import com.coupons.couponsystem.repositoty.PurchaseRepository;
import com.coupons.couponsystem.security.JWTTokenProvider;
import com.coupons.couponsystem.service.UserService;
import com.coupons.couponsystem.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

public abstract  class ClientController {

        @Autowired
        protected AdminServiceImpl adminService;
        @Autowired
        protected CustomerServiceImpl customerService;
        @Autowired
        protected CompanyServiceImpl companyService;

        @Autowired
        protected PasswordEncoder passwordEncoder;

        @Autowired
        protected AuthenticationManager authenticationManager;

        @Autowired
        protected JWTTokenProvider tokenProvider;

        @Autowired
        protected PurchaseRepository purchaseRepository;

        @Autowired
        protected WebClient webClient;

        @Autowired
        protected UserServiceImpl userService;

        @Autowired
        ApplicationContext ctx;

        @Autowired
        LoginManager loginManager;

        @Autowired
        CouponRepository couponRepository;

        @Autowired
        ImageServiceImpl imageService;







     //   public abstract boolean logIn(LogInDOT logInDOT);

}
