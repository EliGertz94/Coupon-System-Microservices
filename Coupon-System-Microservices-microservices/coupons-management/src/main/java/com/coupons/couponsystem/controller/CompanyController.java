package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.TimeframeDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/company/")
public class CompanyController  extends ClientController{

    Logger logger= LoggerFactory.getLogger(CompanyController.class);



    private long companyId;


    @PostMapping("/add-coupon")
    public ResponseEntity<?> addCoupon(@AuthenticationPrincipal SecuredUser userDetails,
                                            @RequestParam("image")MultipartFile file,
                                            @RequestParam("category")Category category,
                                            @RequestParam("title")String title,
                                            @RequestParam("description")String description,
                                            @RequestParam("startDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime startDate,
                                            @RequestParam("endDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                LocalDateTime endDate,
                                            @RequestParam("amount")int amount,
                                            @RequestParam("price")double price){

        try {
            Coupon coupon =Coupon.builder()
                    .amount(amount)
                    .price(price)
                    .category(category)
                    .title(title)
                    .originalAmount(amount)
                    .description(description)
                    .startDate(startDate)
                    .endDate(endDate)
                    .build();
            return new ResponseEntity<>( companyService.addCoupon(userDetails.getUserId(),coupon, file),HttpStatus.OK);

        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @PutMapping("/update-coupon")
    public ResponseEntity<String> updateCoupon(@RequestBody  Coupon coupon,@AuthenticationPrincipal SecuredUser userDetails){
        try {
            companyService.updateCoupon(coupon,userDetails.getUserId());
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        return new ResponseEntity<>("coupon was updated",HttpStatus.OK);
    }

    @GetMapping("/get-image/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable("imageName")String imageName){

           byte[] imageData= imageService.downloadImage(imageName);


            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(imageData);

    }

    @PostMapping("/save-image")
    public ResponseEntity<?> saveImage(@RequestParam("image")MultipartFile file){
        try {
            System.out.println(file.getBytes());
          return new ResponseEntity<>(imageService.uploadImage(file),HttpStatus.OK) ;
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }



    @DeleteMapping("/delete-coupon/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable  Long couponId,@AuthenticationPrincipal SecuredUser userDetails){
        try {
            companyService.deleteCoupon(couponId,userDetails.getUserId());
        } catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }
        return new ResponseEntity<>("coupon was deleted",HttpStatus.OK);
    }

    @GetMapping("/all-coupons")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@AuthenticationPrincipal SecuredUser userDetails){
        System.out.println(userDetails.getUserId()+" at controller");
        System.out.println(companyService.getCompanyId()+" at controller" );
        return new ResponseEntity<>(companyService.getAllCompanyCoupons(userDetails.getUserId()) ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/category/{category}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable String category,@AuthenticationPrincipal SecuredUser userDetails){
        System.out.println(category);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByCategory(category,userDetails.getUserId()) ,HttpStatus.OK);
    }

    @GetMapping("/all-coupons/max-price/{maxPrice}")
    public ResponseEntity<List<Coupon>> getCompanyCoupons(@PathVariable double maxPrice,@AuthenticationPrincipal SecuredUser userDetails){
        System.out.println(maxPrice);
        return new ResponseEntity<>(companyService.getAllCompanyCouponsByPrice(maxPrice,userDetails.getUserId()) ,HttpStatus.OK);
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getCompanyId() {
        return companyId;
    }

    //    @GetMapping("/revenue")
//    public ResponseEntity<?> getRevenueByTimeFrame(@RequestBody TimeframeDTO timeframe){
//        try {
//            return new ResponseEntity<>(companyService.getRevenueByTimeFrame(timeframe.getStart(),timeframe.getEnd()),HttpStatus.OK);
//        } catch (CouponSystemException e) {
//            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
//        }
//    }


}
