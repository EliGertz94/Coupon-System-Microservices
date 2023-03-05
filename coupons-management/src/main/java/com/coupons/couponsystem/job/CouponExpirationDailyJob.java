package com.coupons.couponsystem.job;

import com.coupons.couponsystem.CouponSystemApplication;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.repositoty.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

//@Component
public class CouponExpirationDailyJob {

    public  Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);

   @Autowired
   CouponRepository couponRepository;

       @Scheduled(fixedRate = 10_000)
       @Transactional
        public void dailyJob() {
           List<Coupon> coupons = couponRepository.findAllByEndDateBefore(LocalDateTime.now());

           for (Coupon coupon:
                coupons) {
               coupon.setBuyable(false);
           }
           logger.info("was looking for coupons to disable ");
        }
}


