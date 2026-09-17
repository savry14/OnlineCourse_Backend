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
                        // ── Public: Swagger / OpenAPI ──────────────────────────
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()

                        // ── Public: Auth endpoints ─────────────────────────────
                        .requestMatchers("/api/auth/**").permitAll()

                        // ── Public: read-only Categories & Courses ─────────────
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET, "/api/categories/**"
                        ).permitAll()
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET, "/api/courses/**"
                        ).permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/v1/files/**").permitAll()

                        // ── Public: read-only Sections ────────────────────────
                        .requestMatchers(
                                org.springframework.http.HttpMethod.GET, "/api/sections/**"
                        ).permitAll()

                        // ── Admin only: Category / Course write operations ─────
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,   "/api/categories/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,    "/api/categories/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE, "/api/categories/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.POST,   "/api/courses/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.PUT,    "/api/courses/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                org.springframework.http.HttpMethod.DELETE, "/api/courses/**"
                        ).hasRole("ADMIN")

                        // ── Admin only: User management ────────────────────────
                        .requestMatchers("/api/users").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.GET,    "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/users/{id}").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH,  "/api/users/{id}/role").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH,  "/api/users/{id}/suspend").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PATCH,  "/api/users/{id}/unsuspend").hasRole("ADMIN")

                        // ── Authenticated: own profile & wishlist ──────────────
                        .requestMatchers("/api/users/me/**").authenticated()
                        .requestMatchers("/api/users/{userId}/progress/**").authenticated()

                        // ── Everything else requires authentication ────────────
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
