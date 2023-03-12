package com.coupons.couponsystem.dto.response;

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
public class CompanyDTOResponse extends ResponseMessage {


        private Long id;

        private Long companyId;

        private String name;
        private String username;
        private ClientType clientRole;

        private List<Coupon> coupons ;

        public CompanyDTOResponse(User user, Company company,String message) {
                super(message);
                this.id= user.getId();
                this.companyId= company.getId();
                this.name = company.getName();
                this.username = user.getUsername();

                this.clientRole =user.getClientRole();
                if(company.getCoupons()!=null){
                        this.coupons = company.getCoupons();
                }else{
                        company.setCoupons(new LinkedList<>());
                        this.coupons = company.getCoupons();
                }

        }




}
