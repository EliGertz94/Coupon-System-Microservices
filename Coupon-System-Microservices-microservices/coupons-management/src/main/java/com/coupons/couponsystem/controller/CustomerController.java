package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.dto.CouponIdsDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import com.coupons.couponsystem.security.SecuredUser;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/customer/")
public class CustomerController extends ClientController {


    Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/purchase/")
    public ResponseEntity<?> purchaseCoupon(@RequestBody CouponIdsDTO purchaseIds,@AuthenticationPrincipal SecuredUser userDetails){
        try {
            return new ResponseEntity<>(customerService.makePurchase(purchaseIds.getIds(),userDetails.getUserId()), HttpStatus.OK);

        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());


        } catch (Exception e) {
            return new ResponseEntity<>("No Internet - reconnect ",HttpStatus.UNAUTHORIZED);

        }
    }

    @GetMapping("/all-purchases/")
    public ResponseEntity<?> getCustomerPurchases(@AuthenticationPrincipal SecuredUser userDetails){
    try    {
            return new ResponseEntity<>(customerService.getCustomerPurchases(userDetails.getUserId()), HttpStatus.OK);
    } catch (CouponSystemException e) {
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
    }

//    @GetMapping("/coupons/category/{category}")
//    public ResponseEntity<List<Purchase>> getCustomerCoupons(@PathVariable Category category){
//        return new ResponseEntity<>(customerService.getCustomerPurchases(category),HttpStatus.OK);
//    }

    @GetMapping("/all-purchases/price/{maxPrice}")
    public ResponseEntity<?> getCustomerPurchases(@PathVariable double maxPrice,@AuthenticationPrincipal SecuredUser userDetails){

        try {
            return new ResponseEntity<>(customerService.getCustomerPurchases(maxPrice,userDetails.getUserId()),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<?> getCustomerDetails(@AuthenticationPrincipal SecuredUser userDetails){
        try {
            return  new ResponseEntity<>(customerService.getCustomerDetails(userDetails.getUserId()),HttpStatus.OK);
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
    }

    //Will only return coupons of all active companies

    @GetMapping("/all-coupons")
    public ResponseEntity<?> getCompanyCoupons(){

       try {
            List<Company> companies = companyRepository.findAllByIsActiveTrue()
                    .orElseThrow(()
                            -> new CouponSystemException(" coupon in coupon list was not found ", HttpStatus.NOT_FOUND));

            System.out.println("get all coupons to buy ");
            List<Coupon> coupons = new ArrayList<>();
           List<Coupon> merge = new ArrayList<>();

           for(Company company : companies){
               merge.addAll(company.getCoupons());
           }
            return new ResponseEntity<>(merge, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-coupons/{couponId}")
    public ResponseEntity<?> getOneCouponFromTotal(@PathVariable long couponId )  {
        try{


            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(()
                    -> new CouponSystemException(" coupon in coupon list was not found ", HttpStatus.NOT_FOUND));
            return new ResponseEntity<>(coupon,HttpStatus.OK);


        }catch (CouponSystemException e){
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-image/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable("fileName")String fileName){


        byte[] imageData= imageService.downloadImage(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);

    }




}
