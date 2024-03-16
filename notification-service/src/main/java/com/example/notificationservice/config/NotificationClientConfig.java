package com.example.notificationservice.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class NotificationClientConfig {


    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
