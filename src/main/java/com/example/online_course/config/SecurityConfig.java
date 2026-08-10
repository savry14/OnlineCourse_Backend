package com.example.online_course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // REST API -> disable CSRF
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // Auth API មិនចាំបាច់ login
                        .requestMatchers("/api/auth/**").permitAll()

                        // API ផ្សេងទៀតត្រូវការ authentication
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}