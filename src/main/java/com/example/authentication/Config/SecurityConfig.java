package com.example.authentication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configure authorization requests
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

        // Disable CORS and CSRF
        http.cors().disable().csrf().disable();
        return http.build();

    }
}
