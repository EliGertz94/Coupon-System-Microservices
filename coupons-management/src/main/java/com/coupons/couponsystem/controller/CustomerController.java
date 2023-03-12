package com.coupons.couponsystem.controller;


import com.coupons.couponsystem.dto.request.CouponIdsDTO;
import com.coupons.couponsystem.dto.response.ResponseMessage;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.model.Purchase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/customer/")
public class CustomerController extends ClientController {

    Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/purchase/")
    public ResponseEntity<ResponseMessage> purchaseCoupon(@RequestBody CouponIdsDTO purchaseIds){
        try {

            return CustomResponse.response(HttpStatus.OK,customerService.makePurchase(purchaseIds.getIds()));


        } catch (CouponSystemException e) {
            return CustomResponse.response(e.getHttpStatus(),e.getMessage());
        }
    }


    @GetMapping("/all-purchases/")
    public ResponseEntity<ResponseMessage> getCustomerPurchases(){


        return new ResponseEntity<>(dtoTransition.EntityToDTO(customerService.getCustomerPurchases(),"success"),HttpStatus.OK);

    }


    @GetMapping("/all-purchases/price/{maxPrice}")
    public ResponseEntity<ResponseMessage> getCustomerPurchases(@PathVariable double maxPrice){

        try {
            return new ResponseEntity<>(dtoTransition.EntityToDTO(customerService.getCustomerPurchases(maxPrice),"success"),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }
    }

    @GetMapping("/details")
    public ResponseEntity<ResponseMessage> getCustomerDetails(){
        try {
            return  new ResponseEntity<>(dtoTransition.EntityToDTO(customerService.getCustomerDetails(),"success"),HttpStatus.OK);
        } catch (CouponSystemException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

}
