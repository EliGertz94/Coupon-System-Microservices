package com.coupons.couponsystem.service.impl;


import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.model.User;
import com.coupons.couponsystem.service.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl extends ClientFacade implements UserService {


    /**
     *
     * @param username
     * @param password
     * @param clientType
     * @return
     * @throws CouponSystemException if user not found
     */
    @Override
    public boolean login(String username, String password, String clientType) throws CouponSystemException , AuthenticationException {

                User user = userRepository.findByUsername(username)
                        .orElseThrow(()-> new CouponSystemException("user not found by this username", HttpStatus.NOT_FOUND));




                if(!(user.getPassword().equals(password)) ||  !(user.getClientRole().toString().equals(clientType.toString())))
                {
                    return false;
                }
                 return true;
        }
    }

