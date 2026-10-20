package com.study_tracker.ayush.config;

import com.study_tracker.ayush.auth.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final StudentOwnershipAuthorizationManager ownershipAuthorizationManager;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            StudentOwnershipAuthorizationManager ownershipAuthorizationManager) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.ownershipAuthorizationManager = ownershipAuthorizationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                .authorizeHttpRequests(auth -> auth
                        // Public documentation and health check
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/actuator/health"
                        ).permitAll()

                        // Public login endpoints
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/auth/student",
                                "/api/auth/admin"
                        ).permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Students can submit records only for their own ID.
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/students/{studentId}/records"
                        ).access(ownershipAuthorizationManager)

                        // Students can read their own data; admins can read any student's data.
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/students/{studentId}/**"
                        ).access(ownershipAuthorizationManager)

                        // Deny all other operations on student endpoints.
                        .requestMatchers("/api/students/**").denyAll()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}