package com.coupons.couponsystem.exception.customeResponse;

import com.coupons.couponsystem.dto.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CustomResponse {

    public static ResponseEntity<ResponseMessage> response(HttpStatus httpStatus, String message ){

        ResponseMessage response= new ResponseMessage(message);

        return new ResponseEntity<>(response,httpStatus);
    }
}
