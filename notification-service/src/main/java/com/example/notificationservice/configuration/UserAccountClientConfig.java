package com.example.notificationservice.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class UserAccountClientConfig {


    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
