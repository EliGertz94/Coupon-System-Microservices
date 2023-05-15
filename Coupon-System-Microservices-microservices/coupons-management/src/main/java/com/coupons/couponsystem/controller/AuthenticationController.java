package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.LogInDOT;

import com.coupons.couponsystem.dto.LoginResponse;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.security.UserDetailsServiceImpl;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/authentication")
public class AuthenticationController extends ClientController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInDOT logInDOT) {


        try {
            if(userService.login(logInDOT.getUsername(),logInDOT.getPassword(),logInDOT.getClientRole().toString())) {


                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                logInDOT.getUsername(),
                                logInDOT.getPassword()));

                SecuredUser user = userDetailsService.loadUserByUsername(logInDOT.getUsername());

                String token = tokenProvider.generateToken(user);

                GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);


                return new ResponseEntity<>(new
                        LoginResponse(token,user.getUsername(),grantedAuth.getAuthority())
                        , HttpStatus.OK);


            }
            else {

                throw new CouponSystemException("one of the fields is incorrect",HttpStatus.UNAUTHORIZED);
            }
        }
        catch (CouponSystemException e) {
            return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
        }

        catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }

        catch (AuthenticationException e) {
            System.out.println(e.getMessage() + " authentication exception");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
        }
    }

}



