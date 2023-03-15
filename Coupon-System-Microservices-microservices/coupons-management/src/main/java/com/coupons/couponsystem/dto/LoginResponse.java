package com.coupons.couponsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public class LoginResponse  {
        private String accessToken;
        private final String tokenType = "Bearer ";
        private String username;

        private String grantedAuthority;


                public LoginResponse(String accessToken, String grantedAuthority, String username
                , String message) {

                        this.accessToken = accessToken;
                        this.grantedAuthority = grantedAuthority;
                        this.username= username;
                }


        }
