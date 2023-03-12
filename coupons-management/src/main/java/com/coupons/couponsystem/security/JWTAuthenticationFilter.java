package com.coupons.couponsystem.security;

import com.coupons.couponsystem.clientLogIn.ClientType;
import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.service.impl.CompanyServiceImpl;
import com.coupons.couponsystem.service.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JWTTokenProvider tokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CompanyServiceImpl companyService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

try{
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);

        String username = tokenProvider.getUserNameFromJwt(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            SecuredUser user = (SecuredUser) userDetailsService.loadUserByUsername(username);

            try {
                if (tokenProvider.validateToken(token, user)) {

                    System.out.println("auth of a user   " + user.getAuthorities().stream().toList().get(0).getAuthority());

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                            (user, null, user.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    if (user.getAuthorities().stream().toList().get(0).getAuthority().equals(ClientType.Customer.toString())) {
                        System.out.println("customerService id was initiated ");
                        customerService.setCustomerId(user.getUserId());
                    }
                    if (user.getAuthorities().stream().toList().get(0).getAuthority().equals(ClientType.Company.toString())) {
                        companyService.setCompanyId(user.getUserId());
                        System.out.println("companyService id was initiated ");
                    }

                    filterChain.doFilter(request,response);



                }
            } catch (CouponSystemException e) {
                response.setContentType("application/json");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"outdated json");
                response.getOutputStream().println(
                        "{ \"error\": \"" +response.getStatus()+" "+ "outdated json }");
            }

        }

    }catch(Exception e){

    byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error",e.getMessage()));
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getOutputStream().write(body);

   }
    }


}