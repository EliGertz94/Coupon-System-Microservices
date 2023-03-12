package com.coupons.couponsystem.dto.response;

import com.coupons.couponsystem.model.Purchase;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

public class CustomerDTOResponse extends ResponseMessage {
        private Long id;

        private Long customerId;

        private String firstName;
        private String lastName;
        private String username;



        private List<Purchase> purchases ;

        public CustomerDTOResponse(Long id, Long customerId, String firstName, String lastName, String username, List<Purchase> purchases,String message) {

                super(message);
                this.id = id;
                this.customerId = customerId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.username = username;

                if(purchases== null){
                       purchases= new ArrayList<>();
                }else{
                        this.purchases = purchases;
                }
        }
}
