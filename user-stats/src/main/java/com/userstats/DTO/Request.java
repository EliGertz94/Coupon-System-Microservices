package com.userstats.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Request {

    private long userId;
    private String userName;
    private String userType;
    private double price;


}
