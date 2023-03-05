package com.coupons.couponsystem.security;

import com.coupons.couponsystem.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

    private User user;
    @Override
    public String getAuthority() {
        return user.getClientRole().toString();
    }
}



