package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.dto.CouponIdsDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/customer/")
public class CustomerController extends ClientController {

    Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/purchase/")
    public ResponseEntity<?> purchaseCoupon(@RequestBody CouponIdsDTO purchaseIds){
        try {
            return new ResponseEntity<>(customerService.makePurchase(purchaseIds.getIds()), HttpStatus.OK);

        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());


        } catch (Exception e) {
            return new ResponseEntity<>("No Internet - reconnect ",HttpStatus.UNAUTHORIZED);

        }
    }

    @GetMapping("/all-purchases/")
    public ResponseEntity<?> getCustomerPurchases(Authentication authentication){
    try    {
            return new ResponseEntity<>(customerService.getCustomerPurchases(), HttpStatus.OK);
    } catch (CouponSystemException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
    }

//    @GetMapping("/coupons/category/{category}")
//    public ResponseEntity<List<Purchase>> getCustomerCoupons(@PathVariable Category category){
//        return new ResponseEntity<>(customerService.getCustomerPurchases(category),HttpStatus.OK);
//    }

    @GetMapping("/all-purchases/price/{maxPrice}")
    public ResponseEntity<?> getCustomerPurchases(@PathVariable double maxPrice){

        try {
            return new ResponseEntity<>(customerService.getCustomerPurchases(maxPrice),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails(){
        try {
            return  new ResponseEntity<>(customerService.getCustomerDetails(),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

}
