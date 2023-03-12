package com.coupons.couponsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RequestUserRecord {
    private long userId;
    private String userName;
    private String userType;


}
