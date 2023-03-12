package com.coupons.couponsystem.payPalConfig;

import com.coupons.couponsystem.exception.CouponSystemException;
import com.coupons.couponsystem.exception.customeResponse.CustomResponse;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;


    public Map<String,String> payPaySDKConfig(){
        try {
            Map<String, String> configMap = new HashMap<>();
            configMap.put("mode", mode);
            return configMap;
        }
     catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating API context: " + e.getMessage());
    }
    }


    @Bean
    public OAuthTokenCredential oAuthTokenCredential(){
        return new OAuthTokenCredential(clientId,clientSecret,payPaySDKConfig());
    }

//    @Bean
//    public APIContext apiContext()  {
//        try{
//            APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
//            context.setConfigurationMap(payPaySDKConfig());
//
//            return context;
//        }catch(Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
//        }
//    }

    @Bean
    public APIContext apiContext() {
        try {
            String accessToken = null;

                accessToken = oAuthTokenCredential().getAccessToken();

            APIContext context = new APIContext(accessToken);
            context.setConfigurationMap(payPaySDKConfig());

            return context;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating API context: " + e.getMessage());
        }
    }
}
