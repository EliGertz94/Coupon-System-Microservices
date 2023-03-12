package com.coupons.couponsystem.dto.request;

import com.coupons.couponsystem.clientLogIn.ClientType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
public class LogInDOT {

    private String username;
    private String password;

    private String clientRole;


    public LogInDOT(String username, String password, String clientRole) {

        this.username = username;
        this.password = password;
        this.clientRole = clientRole;


    }


}
