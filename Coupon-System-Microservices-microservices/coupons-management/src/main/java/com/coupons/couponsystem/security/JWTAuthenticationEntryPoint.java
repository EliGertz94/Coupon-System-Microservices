package com.coupons.couponsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String responseMessage="";
        if(  response.getStatus() == 404 ){
             responseMessage = "Page not found";
        }else if(response.getStatus() == 403){
             responseMessage = " Can't reach resources ";
        }
        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error",responseMessage + response.getStatus()));
        response.setContentType("application/json");

        response.setStatus(response.getStatus());
        response.getOutputStream().write(body);
    }


}
