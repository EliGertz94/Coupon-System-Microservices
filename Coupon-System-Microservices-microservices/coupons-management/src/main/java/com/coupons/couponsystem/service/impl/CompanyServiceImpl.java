package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.*;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
@CrossOrigin
@Scope("prototype")
public class CompanyServiceImpl extends ClientFacade  implements CompanyService {


    private long companyId;

    String imagesFilePath= "src\\main\\resources\\static\\images";

    private SecuredUser getUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        return user;
    }

    /**
     * @param email
     * @param password
     * @return
     * @throws CouponSystemException company not found
     */

    public boolean logIn(String email, String password, ClientType clientRole) throws CouponSystemException {

        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new CouponSystemException("user company not found at logIn companyService", HttpStatus.NOT_FOUND));
        if (user.getPassword().equals(password)
                && user.getClientRole().equals(clientRole)) {
            Company company = companyRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new CouponSystemException("company not found by user at logIn companyService", HttpStatus.NOT_FOUND));

            companyId = company.getId();
            return true;
        }
        return false;
    }


    /**
     * @param coupon
     * @return
     * @throws CouponSystemException company not found , title already exits
     */
    @Override
    public Coupon addCoupon(Long companyId, Coupon coupon, MultipartFile file) throws CouponSystemException, IOException {

        String originalFileName = file.getOriginalFilename();

        String absolutePath ="C:\\Users\\elige\\Downloads\\Coupon-System-Microservices-microservices\\Coupon-System-Microservices-microservices\\coupons-management\\src\\main\\resources\\frontend\\coupon-sys-admin\\src\\images";

                //Paths.get(imagesFilePath).toAbsolutePath().toString();
        System.out.println(absolutePath + "  absolute path ");
        File destinationFile = new File(absolutePath,originalFileName);
        System.out.println(destinationFile);
//        try {
            file.transferTo(destinationFile);
//        } catch (IOException e) {
//            throw new CouponSystemException("File addCoupon IOException ", HttpStatus.BAD_REQUEST);
//        }


        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CouponSystemException("company not found at add coupon at companyService", HttpStatus.NOT_FOUND));

        if (!company.isActive()) {
            throw new CouponSystemException("company is not active", HttpStatus.BAD_REQUEST);
        }

       // ImageData imageData = imageService.uploadImage(file);
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), companyId)) {

            throw new CouponSystemException("title already exits -  at add coupon at companyService", HttpStatus.BAD_REQUEST);
        }
        coupon.setCompany(company);
        coupon.setImage(destinationFile.toString());

        //coupon.setImageData(imageData);
        coupon.setBuyable(true);


        return couponRepository.save(coupon);
    }

    /**
     * @param couponId
     * @throws CouponSystemException - coupon not found - coupon doesn't belong to the company
     */
    @Override
    public void deleteCoupon(long couponId, long companyId) throws CouponSystemException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CouponSystemException("company not found at add coupon at companyService", HttpStatus.NOT_FOUND));

        System.out.println("companyId " + companyId);
        if (!company.isActive()) {
            throw new CouponSystemException("company is not active", HttpStatus.BAD_REQUEST);
        }

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponSystemException("coupon not found at deleteCoupon companyService ", HttpStatus.NOT_FOUND));
        if (coupon.getCompany().getId() != companyId) {
            throw new CouponSystemException("coupon doesn't belong to the company at deleteCoupon CompanyService", HttpStatus.BAD_REQUEST);
        }
        couponRepository.delete(coupon);

    }

    /**
     * @param coupon
     * @return
     * @throws CouponSystemException - coupon not found - coupon doesn't belong to the company
     */
    @Override
    public Coupon updateCoupon(Coupon coupon, long userId) throws CouponSystemException {


        Company company = companyRepository.findById(userId)
                .orElseThrow(() -> new CouponSystemException("company not found ", HttpStatus.NOT_FOUND));


        if (!company.isActive()) {
            throw new CouponSystemException("company is not active", HttpStatus.BAD_REQUEST);
        }

        if (coupon.getCompany().getId() == userId) {


            return couponRepository.findById(coupon.getId()).map(couponEntity -> {
                        couponEntity.setCategory(coupon.getCategory());
                        couponEntity.setTitle(coupon.getTitle());
                        couponEntity.setDescription(coupon.getDescription());
                        couponEntity.setStartDate(coupon.getStartDate());
                        couponEntity.setEndDate(coupon.getEndDate());
                        couponEntity.setPrice(coupon.getPrice());
//                     couponEntity.setAmount(coupon.getAmount());
//                     couponEntity.setImage(coupon.getImage());

                        return couponEntity;
                    }
            ).orElseThrow(() -> new CouponSystemException("coupon not found at updateCoupon CompanyService", HttpStatus.NOT_FOUND));
//     }

            //
        }

        return null;
    }



    /**
     *
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCoupons(long companyId) {

        System.out.println("company is at getAllcoupons "+companyId );

        List<Coupon> coupons = couponRepository.findAllByCompany_id(companyId);
         return coupons;
    }

    /**
     *
     * @param category
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCouponsByCategory(String category,long companyId) {
        return couponRepository.findAllByCompany_idAndCategory(companyId,Category.valueOf(category));
    }

    /**
     *
     * @param maxPrice
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCouponsByPrice(double maxPrice,long companyId) {

       List<Coupon> coupons =  couponRepository.findAllByCompany_idAndPriceLessThanEqual(companyId,maxPrice);

       return coupons;
    }

    /**
     *
     * @return
     * @throws CouponSystemException -company not found
     */
    @Override
    public Company getCompanyDetails(long companyId) throws CouponSystemException {

       return companyRepository.findFullCompany(companyId)
               .orElseThrow(() -> new CouponSystemException("company not found at getCompanyDetails CompanyService",HttpStatus.NOT_FOUND ));


    }

    public double getRevenueByTimeFrame(LocalDateTime start, LocalDateTime end) throws CouponSystemException {

        double totalRevenue= 0;

        List<Purchase> purchases = purchaseRepository.findAllByPurchaseDateBetween(start,end)
                .orElseThrow(() -> new CouponSystemException("purchases not found by between dates ",HttpStatus.NOT_FOUND ));


        for (Purchase purchase:
             purchases) {
            totalRevenue+= purchase.getTotalPrice();
        }

        return totalRevenue;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
