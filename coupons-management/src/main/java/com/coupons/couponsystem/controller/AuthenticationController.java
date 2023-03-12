package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.dto.request.LogInDOT;
import com.coupons.couponsystem.dto.response.LoginResponse;
import com.coupons.couponsystem.dto.response.ResponseMessage;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.security.UserDetailsServiceImpl;
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
    public ResponseEntity<ResponseMessage> login(@RequestBody  LogInDOT logInDOT) {


        try {
            if(userService.login(logInDOT.getUsername(),logInDOT.getPassword(),logInDOT.getClientRole().toString())) {

                System.out.println("logInDOT input "+logInDOT.getUsername() +" " +logInDOT.getPassword());

                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                logInDOT.getUsername(),
                                logInDOT.getPassword()));

                SecuredUser user = userDetailsService.loadUserByUsername(logInDOT.getUsername());
                System.out.println("user id auth" + user.getUserId());
                String token = tokenProvider.generateToken(user);
                GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);


                return new ResponseEntity<>(new
                        LoginResponse(token,grantedAuth.getAuthority(), user.getUsername(),"successfully logged in")
                        , HttpStatus.OK);


            }
            else {

               throw new CouponSystemException("one of the fields is incorrect",HttpStatus.UNAUTHORIZED);
            }
        }
        catch (CouponSystemException e) {

            return CustomResponse.response(HttpStatus.UNAUTHORIZED,e.getMessage());
        }

        catch (UsernameNotFoundException e) {
            return CustomResponse.response(HttpStatus.UNAUTHORIZED,e.getMessage());
        }

        catch (AuthenticationException e) {
            System.out.println(e.getMessage() + " authentication exception");
            return CustomResponse.response(HttpStatus.UNAUTHORIZED,e.getMessage());
        }
    }

}



