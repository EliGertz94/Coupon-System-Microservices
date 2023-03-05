package com.coupons.couponsystem.dto;

import com.coupons.couponsystem.clientLogIn.ClientType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LogInDOT {

    private String username;
    private String password;

    private ClientType clientRole;


    public LogInDOT(String username, String password, ClientType clientRole) {
        this.username = username;
        this.password = password;
        this.clientRole = clientRole;
    }
}
