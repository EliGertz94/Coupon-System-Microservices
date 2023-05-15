package com.coupons.couponsystem.clientLogIn;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.service.impl.AdminServiceImpl;
import com.coupons.couponsystem.service.impl.ClientFacade;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@ComponentScan
@Component
@Scope // change to prototype
public class LoginManager {


        @Autowired
        AdminServiceImpl adminService;

        @Autowired
        ApplicationContext atx;

//        @Autowired
//        CompanyServiceImpl companyService;
//
//        @Autowired
//        CustomerServiceImpl customerService;
//

    public ClientFacade logIn(String email, String password, ClientType clientType) throws CouponSystemException {

            switch (clientType) {
                case Administrator:
                    if(adminService.logIn(email,password,clientType)){
                        return adminService;
                    }

                    break;
                case Company:
                    CompanyServiceImpl company = atx.getBean(CompanyServiceImpl.class);
                    if(company.logIn(email,password,clientType))
                    {
                    return company; }

                break;
            case Customer:
                CustomerServiceImpl customer = atx.getBean(CustomerServiceImpl.class);

                if(customer.logIn(email,password,clientType)){
                    return customer;
                }
                break;
        }
        return null;
    }
}
