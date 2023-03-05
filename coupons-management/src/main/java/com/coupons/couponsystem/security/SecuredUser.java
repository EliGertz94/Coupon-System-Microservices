package com.coupons.couponsystem.security;

import com.coupons.couponsystem.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



public class SecuredUser implements UserDetails {
    private User user;
    private long userId;

    public SecuredUser(User user) {
        this.user = user;
    }

    public SecuredUser(User user, long userId) {
        this.user = user;
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SecurityAuthority> authoreties = new LinkedList<>();
        authoreties.add(new SecurityAuthority(user));
        return authoreties;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
