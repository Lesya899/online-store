package org.nikdev.oauth2authorizationserver.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;


@Component
public class CorsConfig {

    public void corsConfiguration(HttpSecurity http) throws Exception {
        http.cors(cors -> {
            CorsConfigurationSource source = s -> {
                CorsConfiguration corsConfig = new CorsConfiguration();
                corsConfig.setAllowCredentials(true);
                corsConfig.setAllowedOrigins(List.of("http://localhost:8765"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowedMethods(List.of("*"));
                return corsConfig;
            };
            cors.configurationSource(source);
        });
    }
}
