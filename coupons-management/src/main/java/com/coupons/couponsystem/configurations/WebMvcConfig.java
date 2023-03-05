package com.coupons.couponsystem.configurations;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
//                .allowedOrigins("http://localhost:8082")
                .allowedMethods("GET","POST","PUT","HEAD","OPTIONS")
                .allowCredentials(true);
    }
}
