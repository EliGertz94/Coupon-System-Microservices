package com.coupons.couponsystem.exception;

//business logic Exception


import org.springframework.http.HttpStatus;

public class CouponSystemException extends Exception {

    private HttpStatus httpStatus;


    public CouponSystemException() {
    }

    public CouponSystemException(String message, HttpStatus status) {
        super(message);
        this.httpStatus= status;
    }


    public CouponSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouponSystemException(Throwable cause) {
        super(cause);
    }

    public CouponSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}