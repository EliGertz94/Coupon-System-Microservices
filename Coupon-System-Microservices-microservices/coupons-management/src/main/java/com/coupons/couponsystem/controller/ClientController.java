package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.repositoty.PurchaseRepository;
import com.coupons.couponsystem.security.JWTTokenProvider;
import com.coupons.couponsystem.service.UserService;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import com.coupons.couponsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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







     //   public abstract boolean logIn(LogInDOT logInDOT);

}
