package com.coupons.couponsystem.dto;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.model.Company;
import com.coupons.couponsystem.model.Coupon;
import com.coupons.couponsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {


        private Long id;

        private Long companyId;

        private String name;
        private String username;
        private String password;

        private ClientType clientRole;


        private List<Coupon> coupons ;

        public CompanyDTO(User user, Company company) {
                this.id= user.getId();
                this.companyId= company.getId();
                this.name = company.getName();
                this.password= user.getPassword();
                this.clientRole =user.getClientRole();
                if(company.getCoupons()!=null){
                        this.coupons = company.getCoupons();
                }else{
                        company.setCoupons(new LinkedList<>());
                        this.coupons = company.getCoupons();
                }

        }




}
