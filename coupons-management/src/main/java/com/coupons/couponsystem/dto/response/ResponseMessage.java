package com.coupons.couponsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor

public class ResponseMessage {


    private String message;


        public ResponseMessage(String message){
            this.message=message;

        }

}
