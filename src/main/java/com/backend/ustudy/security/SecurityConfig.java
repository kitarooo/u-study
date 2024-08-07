package com.backend.ustudy.security;

import com.backend.ustudy.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public String[] PERMIT_ALL = {
            "/swagger*/**",
            "/swagger-ui/**",
            "/backend/swagger-ui.html",
            "/documentation/**",
            "/v3/api-docs/**",
            "/api/v1/auth/**"
    };

    public String[] USER_ENDPOINTS = {
            "/api/v1/courses/all",
            "/api/v1/courses/{id}",
            "/api/v1/lessons/{id}",
            "/api/v1/lessons/{courseId}/{id}"
    };

    public String[] ADMIN_ENDPOINTS = {
            "/api/v1/courses/create",
            "/api/v1/courses/delete/{id}",
            "/api/v1/courses/updateImage/{id}",
            "/api/v1/lessons//{courseId}/{id}",
            "/api/v1/lessons/upload{id}"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(USER_ENDPOINTS).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(PERMIT_ALL).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
