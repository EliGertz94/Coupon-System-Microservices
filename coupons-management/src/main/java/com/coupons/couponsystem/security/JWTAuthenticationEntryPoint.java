package com.coupons.couponsystem.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        response.sendError(response.getStatus()," "+ authException.getMessage());
//        ResponseStatusException
        response.getOutputStream().println(
                "{ \"error\": \"" +response.getStatus()+" "+ authException.getMessage() + " "+ authException.getLocalizedMessage()+" }");

    }


}
