package org.nikdev.oauth2authorizationserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {


    @Bean
    public BCryptPasswordEncoder bcryptEncoder() {
        return  new BCryptPasswordEncoder();
    }
}
