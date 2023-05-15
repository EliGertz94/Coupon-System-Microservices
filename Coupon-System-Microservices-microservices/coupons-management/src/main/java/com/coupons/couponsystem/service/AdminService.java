package com.coupons.couponsystem.service;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.User;

import java.util.List;


public interface AdminService {

 //   boolean existsBy(String email,String password) throws Exception;

//    public boolean logIn(String email,String password) throws CouponSystemException;

    Company addCompany(User user,Company company) throws CouponSystemException;

    Company updateCompany(User user,Company company) throws CouponSystemException;

    Company deleteCompany(long id) throws CouponSystemException;

    List<Company> getAllCompanies();

    Company getOneCompany(long id) throws CouponSystemException;

    Customer addCustomer(User user,Customer customer) throws CouponSystemException;

    Customer updateCustomer(User user,Customer customer) throws CouponSystemException;

    void deleteCustomer(long id) throws CouponSystemException;

    List<Customer> getAllCustomers();

    Customer getOneCustomer(long id) throws CouponSystemException;


}
