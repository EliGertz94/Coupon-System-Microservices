package com.coupons.couponsystem.configurations;

import com.coupons.couponsystem.security.JWTAuthenticationEntryPoint;
import com.coupons.couponsystem.security.JWTAuthenticationFilter;
import com.coupons.couponsystem.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    private JWTAuthenticationEntryPoint jwtEntryPoint;


    @Autowired
    public SecurityConfig(JWTAuthenticationEntryPoint jwtEntryPoint) {
        this.jwtEntryPoint = jwtEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable().
        exceptionHandling()
        .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

      http.authorizeHttpRequests()
              .requestMatchers("/src/**").permitAll()

              .requestMatchers("/images/**").permitAll()
              .requestMatchers("/api/authentication/**").permitAll()
              .requestMatchers("/api/authentication/**").permitAll()
              .requestMatchers("/api/paypal/**").permitAll()
              .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
              .requestMatchers("/api/admin/**").hasAuthority("Administrator")
//              .requestMatchers("/api/company/all-coupons/**").hasAuthority("Customer")

              .requestMatchers("/api/company/**").hasAuthority("Company")
              .requestMatchers("/api/customer/**").hasAuthority("Customer")


              //http://localhost:8081/api/company/all-coupons
              .anyRequest().authenticated()
//                .and()
//                .formLogin()
                .and()
                .httpBasic();

      //http.oauth2ResourceServer().jwt();
              http.cors(Customizer.withDefaults());

            http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

      return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder(){
        return  NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
            return auth.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(){
        return new UserDetailsServiceImpl();
    }




//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:3000/"));
//        configuration.setAllowedMethods(Arrays.asList("POST","GET"));
//       // configuration.setAllowedHeaders(List.of("Authorization"));
//       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//


}

