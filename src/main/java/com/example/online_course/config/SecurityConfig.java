package com.example.online_course.config;

import com.example.online_course.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
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
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/password/**").permitAll()
                        .requestMatchers("/api/section-ocr/getAll").permitAll()
                        .requestMatchers("/api/section-ocr/getById/**").permitAll()
                        .requestMatchers("/api/category/create").authenticated()
                        .requestMatchers("/api/category/getAll").permitAll()
                        .requestMatchers("/api/category/getById/**").permitAll()
                        .requestMatchers("/api/category/update/**").hasRole("ADMIN")
                        .requestMatchers("/api/category/delete/**").hasRole("ADMIN")
                        .requestMatchers("/api/course/create").authenticated()
                        .requestMatchers("/api/course/getAll").permitAll()
                        .requestMatchers("/api/course/getById/**").permitAll()
                        .requestMatchers("/api/course/update/**").authenticated()
                        .requestMatchers("/api/course/delete/**").authenticated()
                        .requestMatchers("/api/users/get-profile").authenticated()
                        .requestMatchers("/api/users/update-profile").authenticated()
                        .requestMatchers("/api/users/me/change-password").authenticated()
                        .requestMatchers("/api/users").hasRole("ADMIN")
                        .requestMatchers("/api/users/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"message\": \"Unauthorized: Missing or invalid token\", \"status\": 401}"
                            );
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json");
                            response.getWriter().write(
                                    "{\"message\": \"Forbidden: You do not have permission to access this resource\", \"status\": 403}"
                            );
                        })
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
