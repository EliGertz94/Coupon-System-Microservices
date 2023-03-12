package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.TimeframeDTO;
import com.coupons.couponsystem.dto.response.ResponseMessage;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/company/")
public class CompanyController  extends ClientController{

    Logger logger= LoggerFactory.getLogger(CompanyController.class);


    @PostMapping("/add")
    public ResponseEntity<ResponseMessage> addCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.addCoupon(coupon);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
        return CustomResponse.response(HttpStatus.OK,"coupon was added!");
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.updateCoupon(coupon);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());

        }

        return CustomResponse.response(HttpStatus.OK,"Coupon is up to date");
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<ResponseMessage> deleteCoupon(@PathVariable  Long couponId){
        try {
            companyService.deleteCoupon(couponId);
        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
        return CustomResponse.response(HttpStatus.OK,"coupon was deleted");

    }

    @GetMapping("/all-coupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(){
        return new ResponseEntity<>(companyService.getAllCompanyCoupons() ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/category/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String category){
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByCategory(category) ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/max-price/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice){
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByPrice(maxPrice) ,HttpStatus.OK);
    }



}
