package com.coupons.couponsystem.dto.request;

import com.coupons.couponsystem.model.Purchase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class CustomerDTO {
        private Long id;

        private Long customerId;

        private String firstName;
        private String lastName;
        private String username;
        private String password;


        private List<Purchase> purchases ;

        public CustomerDTO(Long id, Long customerId, String firstName, String lastName, String username, String password, List<Purchase> purchases) {
                this.id = id;
                this.customerId = customerId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;
                this.password = password;
                if(purchases== null){
                       purchases= new ArrayList<>();
                }else{
                        this.purchases = purchases;
                }
        }
}
