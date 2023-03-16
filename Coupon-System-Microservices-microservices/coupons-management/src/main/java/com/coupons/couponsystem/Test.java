package com.coupons.couponsystem;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.clientLogIn.LoginManager;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.*;
import com.coupons.couponsystem.repositoty.UserRepository;
import com.coupons.couponsystem.service.CompanyService;
import com.coupons.couponsystem.service.CustomerService;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Test implements CommandLineRunner {
    @Autowired
    LoginManager loginManager;

    @Autowired
    UserRepository userRepository;

    public static Logger logger = LoggerFactory.getLogger(CouponSystemApplication.class);


    @Override
    public void run(String... args) throws Exception {

        //to start off with the admin...

        User user = User.builder()
                .id(0)
                .password("123456")
                .username("admin@admin.com")
                .clientRole(ClientType.Administrator)
                .build();

        if(!userRepository.existsByUsername(user.getUsername())){
            userRepository.save(user);
            System.out.println("first admin for test was added");

        }else{
            System.out.println("first admin for test is ready");
        }

        //loginManager and service functionality

        try{

            AdminServiceImpl admin = (AdminServiceImpl) loginManager.logIn
                    ("admin@admin.com", "123456", ClientType.Administrator);
            if(admin!=null) {

                User userCompanyRecord = User.builder()
                        .id(0)
                        .password("123456")
                        .username("companyinc5@gmail.com")
                        .clientRole(ClientType.Company)
                        .build();

                Company companyRecord = Company.builder()
                        .id(0L)
                        .name("companyInc3")
                        .coupons(null)
                        .build();

               //  admin.addCompany(userCompanyRecord,companyRecord);

                User userCustomerRecord = User.builder()
                        .id(0)
                        .password("123456")
                        .username("customer1@gmail.com")
                        .clientRole(ClientType.Customer)
                        .build();



                Customer customerRecord = new Customer(0, "Shalom", "Wozzeck", new ArrayList<>());


                // Customer returnedCustomer = admin.addCustomer(userCustomerRecord,customerRecord);

                // returnedCustomer.setLastName("Shimony");

                //  admin.deleteCompany(17);

                //    admin.updateCustomer(returnedCustomer.getUser(),returnedCustomer);
                //admin.deleteCustomer(10);

            }
            Coupon coupon = new Coupon(0L, null, Category.FOOD,
                    "falafel world", "description",
                    LocalDateTime.of(2023, 03, 01, 12, 12),
                    LocalDateTime.of(2023, 07, 01, 12, 12),
                    150, 80,
                    "image", true,
                    new ArrayList<>());

            CompanyServiceImpl company = (CompanyServiceImpl) loginManager.logIn("companyinc@gmail.com","123456",ClientType.Company);
            if(company!=null) {

                // company.addCoupon(coupon);

                // company.deleteCoupon(11);

                }
                CustomerServiceImpl customer = (CustomerServiceImpl) loginManager.logIn
                        ("haviv@gmail.com", "1AAA@313132", ClientType.Customer);
            if(customer!=null){
                // --------- make purchase ----------
                //will return a string to a PayPal website to make the transaction
                // you need internet connection
                //click on the link in the console

                //credentials to use for PayPal (to make "payment"):
                // email: sb-umpku25079892@personal.example.com
                //password : AA123456

                //list can have as many ids you want plus duplicates
                // once payment is approved purchase will be added
                // and also every coupon individually as a record to purchase_stats in other microservice
                List<Long> couponListIds = new ArrayList<>();
                couponListIds.add(7L);
                couponListIds.add(8L);

                //make purchase
                System.out.println(customer.makePurchase(couponListIds));


                List<Purchase> purchases = customer.getCustomerPurchases();

            }

        } catch(CouponSystemException e){
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }




}
