package com.expensetracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable().cors().disable();

        //http.authorizeHttpRequests().anyRequest().permitAll();

        http.addFilterBefore(jwtFilter,  UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/user/register", "/api/v1/auth/login")
                .permitAll()
                .anyRequest().authenticated();

         return  http.build();



    }


}
