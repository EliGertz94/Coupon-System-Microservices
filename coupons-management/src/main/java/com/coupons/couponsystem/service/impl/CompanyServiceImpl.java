package com.coupons.couponsystem.service.impl;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.*;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class CompanyServiceImpl extends ClientFacade  implements CompanyService {


  private long companyId;
    Logger logger= LoggerFactory.getLogger(CompanyServiceImpl.class);



    private SecuredUser getUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser)authentication.getPrincipal();
        logger.info("user at postconstruct user {}" ,user);
        return user;
    }

    /**
     *
     * @param email
     * @param password
     * @return
     * @throws CouponSystemException company not found
     */
    @Override
    public boolean logIn(String email, String password, ClientType clientRole) throws CouponSystemException {

        User user =userRepository.findByUsername(email)
                .orElseThrow(() -> new CouponSystemException("user company not found at logIn companyService",HttpStatus.NOT_FOUND));
        if(user.getPassword().equals(password)
        && user.getClientRole().equals(clientRole)){
            Company company = companyRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new CouponSystemException("company not found by user at logIn companyService", HttpStatus.NOT_FOUND));

            companyId = company.getId();
            return true;
        }
        return false;
    }


    /**
     *
     * @param coupon
     * @return
     * @throws CouponSystemException company not found , title already exits
     *
     */
    @Override
    public Coupon addCoupon(Coupon coupon) throws CouponSystemException {
        Company company = companyRepository.findById(this.companyId)
                .orElseThrow(() -> new CouponSystemException("company not found at add coupon at companyService",HttpStatus.NOT_FOUND ));
        logger.debug("coupon check statment {}"
                ,couponRepository.existsByTitleAndCompanyId(coupon.getTitle(),this.companyId) );

        if(couponRepository.existsByTitleAndCompanyId(coupon.getTitle(),this.companyId)){

            throw new CouponSystemException("title already exits -  at add coupon at companyService",HttpStatus.BAD_REQUEST);
        }
        coupon.setCompany(company);
        coupon.setBuyable(true);

        return  couponRepository.save(coupon);
    }

    /**
     *
     * @param coupon
     * @return
     * @throws CouponSystemException - coupon not found - coupon doesn't belong to the company
     */
    @Override
    public Coupon updateCoupon(Coupon coupon) throws CouponSystemException {

     if(coupon.getCompany().getId()==this.companyId) {
         //
         return couponRepository.findById(coupon.getId()).map(couponEntity -> {
                     couponEntity.setCategory(coupon.getCategory());
                     couponEntity.setTitle(coupon.getTitle());
                     couponEntity.setDescription(coupon.getDescription());
                     couponEntity.setStartDate(coupon.getStartDate());
                     couponEntity.setEndDate(coupon.getEndDate());
                     couponEntity.setAmount(coupon.getAmount());
                     couponEntity.setPrice(coupon.getPrice());
                     couponEntity.setImage(coupon.getImage());

                     return couponEntity;
                 }
         ).orElseThrow(() -> new CouponSystemException("coupon not found at updateCoupon CompanyService",HttpStatus.NOT_FOUND));
     }

    throw new CouponSystemException("coupon doesn't belong to the company at updateCoupon CompanyService",HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param couponId
     * @throws CouponSystemException - coupon not found - coupon doesn't belong to the company
     */
    @Override
    public void deleteCoupon(long couponId) throws CouponSystemException {
        System.out.println("companyId "+ companyId);


        Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new CouponSystemException("coupon not found at deleteCoupon companyService ", HttpStatus.NOT_FOUND));
            if(coupon.getCompany().getId()!= this.companyId){
                throw new CouponSystemException("coupon doesn't belong to the company at deleteCoupon CompanyService",HttpStatus.BAD_REQUEST);
            }
            couponRepository.delete(coupon);

    }

    /**
     *
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCoupons() {

        List<Coupon> coupons = couponRepository.findAllByCompany_id(this.companyId);
         return coupons;
    }

    /**
     *
     * @param category
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCouponsByCategory(String category) {
        return couponRepository.findAllByCompany_idAndCategory(this.companyId,Category.valueOf(category));
    }

    /**
     *
     * @param maxPrice
     * @return List<Coupon>
     */
    @Override
    public List<Coupon> getAllCompanyCouponsByPrice(double maxPrice) {

       List<Coupon> coupons =  couponRepository.findAllByCompany_idAndPriceLessThanEqual(this.companyId,maxPrice);

       return coupons;
    }

    /**
     *
     * @return
     * @throws CouponSystemException -company not found
     */
    @Override
    public Company getCompanyDetails() throws CouponSystemException {

       return companyRepository.findFullCompany(this.companyId)
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
