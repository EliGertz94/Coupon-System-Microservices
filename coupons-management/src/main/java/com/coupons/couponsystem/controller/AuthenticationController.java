package com.coupons.couponsystem.controller;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.dto.LogInDOT;
import com.coupons.couponsystem.dto.ResponseDTO;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.security.SecuredUser;
import com.coupons.couponsystem.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("api/authentication")
public class AuthenticationController extends ClientController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody  LogInDOT logInDOT) {
    try{


        if(logInDOT.getClientRole().equals(ClientType.Administrator)) {
            if(!adminService.logIn(logInDOT.getUsername(), logInDOT.getPassword(),logInDOT.getClientRole())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"wrong credentials ");
            }
        } else if (logInDOT.getClientRole().equals(ClientType.Company)) {
            if(!companyService.logIn(logInDOT.getUsername(), logInDOT.getPassword(),logInDOT.getClientRole())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"wrong credentials ");
            }
        } else if (logInDOT.getClientRole().equals(ClientType.Customer)) {
            if(!customerService.logIn(logInDOT.getUsername(), logInDOT.getPassword(),logInDOT.getClientRole())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"wrong credentials ");

            }
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInDOT.getUsername(),
                        logInDOT.getPassword()));

        SecuredUser user = userDetailsService.loadUserByUsername(logInDOT.getUsername());
        System.out.println("user id auth"+ user.getUserId());
        String token= tokenProvider.generateToken(user);
        GrantedAuthority grantedAuth = user.getAuthorities().stream().toList().get(0);


        return new ResponseEntity<>(new ResponseDTO(token,grantedAuth,user.getUsername()),HttpStatus.OK);

        } catch (CouponSystemException e) {
            throw new ResponseStatusException(e.getHttpStatus(),e.getMessage());
        }

    }

}
