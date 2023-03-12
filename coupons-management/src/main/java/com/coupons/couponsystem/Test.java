package com.coupons.couponsystem;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.clientLogIn.LoginManager;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.Category;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.Customer;
import com.coupons.couponsystem.service.CompanyService;
import com.coupons.couponsystem.service.CustomerService;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Test implements CommandLineRunner {

    @Autowired
    LoginManager loginManager;

    public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);

    @Override
    public void run(String... args) throws Exception{}
//    {
//
//        CompanyService company;
//
//        {
//            try {
//                company = (CompanyServiceImpl)
//                        loginManager.logIn("bbb@bbb.com", "bbb123", ClientType.Company);
//            } catch (CouponSystemException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
////        CompanyService company1 = (CompanyServiceImpl)
////                loginManager.logIn("ccc@ccc.com", "ccc123", ClientType.Company);
////
//
//        //
//        AdminServiceImpl admin;
//
//        {
//            try {
//                admin = (AdminServiceImpl) loginManager.logIn
//                        ("admin@admin.com","admin", ClientType.Administrator);
//            } catch (CouponSystemException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        Company company1 = new Company(0L,"ccc",null);
//        // admin.addCompany(company1);
//        Customer customerEntity = new Customer(0L,"eli","yuval","eli@dgdf.com","12345",null);
//        //admin.addCustomer(customerEntity);
////        Customer customerEntity2 = new Customer(0L,"yanna","grtz","yamma@dgdf.com","12345",null);
//        // admin.addCustomer(customerEntity);
//
//        //  Company company1 = new Company(0L,"ccc","ccc@ccc.com","ccc123",null);
//        //Company company2 = new Company(0L,"bbb","bbb@bbb.com","bbb123",null);
//        //  admin.addCompany(company1);
//        // company.deleteCoupon(3L);
//
//        List<Coupon> couponsByCategory = company.getAllCompanyCouponsByCategory(Category.Restaurant);
//        Coupon couponEntitty1 = new Coupon(0L,null, Category.FOOD,"food lalla coupon","description",
//                LocalDateTime.now(), LocalDateTime.of(2023, 11, 11, 12, 12),150,80,"image",null);
//        Coupon couponEntitty2 = new Coupon(0L,null,Category.FOOD,"food second coupon","description",
//                LocalDateTime.now(), LocalDateTime.of(2022, 11, 11, 12, 12),150,50,"image",null);
//        Coupon couponEntitty3 = new Coupon(0L,null,Category.Restaurant,"food Restaurant coupon","description",
//                LocalDateTime.now(), LocalDateTime.of(2022, 11, 11, 12, 12),150,100,"image",null);
//
//        //company.addCoupon(couponEntitty1);
//        //        company.addCoupon(couponEntitty2);
//        //        company .addCoupon(couponEntitty3);
//        List<Coupon> couponsByPrice = company.getAllCompanyCouponsByPrice(70);
//
//
//        printList(couponsByPrice);
//        //       logger.info("company object {} its coupon {}",company.getCompanyDetails(),company.getCompanyDetails().getCoupons());
//        //
//        CustomerService customer1 = (CustomerService) loginManager.logIn
//                ("eli@dgdf.com","12345", ClientType.Customer);
////        CustomerService customer2 = (CustomerService) loginManager.logIn
////                ("eli@dgdf.com","12345",ClientType.Customer);
////
////
//        //  customer1.purchaseCoupon(10);
////      customer2.purchaseCoupon(8);
//
////        customer.purchaseCoupon(1);
//        //  admin.deleteCompany(3);
//
//        //    admin.deleteCustomer(5);
//        ///   company.deleteCoupon(6);
//        System.out.println("80");
//        printList(customer1.getCustomerCoupons(80));
//        System.out.println("120");
//        printList(customer1.getCustomerCoupons(120));
//        System.out.println("restaurant");
//        printList(customer1.getCustomerCoupons(Category.FOOD));
//        Customer customerE = customer1.getCustomerDetails();
//
//        System.out.println(customerE.getFirstName()+" "+ customerE.getEmail() +" " + customerE.getCoupons());
//
//
//
//
//    }





    public static void printList(List<Coupon> list){
        int i =0;
        for(Coupon  coupon :list){
            logger.info(" coupon "+ i+"  {}",coupon.getTitle());
            i++;
        }
    }


}
