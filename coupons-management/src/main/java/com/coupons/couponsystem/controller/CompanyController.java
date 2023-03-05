package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.TimeframeDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
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
    public ResponseEntity<String> addCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.addCoupon(coupon);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
        return new ResponseEntity<>("coupon was added",HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCoupon(@RequestBody  Coupon coupon){
        try {
            companyService.updateCoupon(coupon);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());

        }
        return new ResponseEntity<>("coupon was updated",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable  Long couponId){
        try {
            companyService.deleteCoupon(couponId);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
        return new ResponseEntity<>("coupon was deleted",HttpStatus.OK);
    }

    @GetMapping("/all-coupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(){
        return new ResponseEntity<>(companyService.getAllCompanyCoupons() ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/category/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String category){
        System.out.println(category);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByCategory(category) ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/max-price/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice){
        System.out.println(maxPrice);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByPrice(maxPrice) ,HttpStatus.OK);
    }


    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenueByTimeFrame(@RequestBody TimeframeDTO timeframe){
        try {
            return new ResponseEntity<>(companyService.getRevenueByTimeFrame(timeframe.getStart(),timeframe.getEnd()),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
    }


}
