package com.example.online_course.config;

import com.example.online_course.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/password/**").permitAll()
                        .requestMatchers("/api/category/create").authenticated()
                        .requestMatchers("/api/category/getAll").permitAll()
                        .requestMatchers("/api/category/getById/**").permitAll()
                        .requestMatchers("/api/category/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/category/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/course/create").authenticated()
                        .requestMatchers("/api/course/getAll").permitAll()
                        .requestMatchers("/api/course/getById/**").permitAll()
                        .requestMatchers("/api/course/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/course/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/lesson/create").authenticated()
                        .requestMatchers("/api/lesson/getAll").permitAll()
                        .requestMatchers("/api/lesson/getById/**").permitAll()
                        .requestMatchers("/api/lesson/by-course/**").permitAll()
                        .requestMatchers("/api/lesson/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/lesson/delete/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
