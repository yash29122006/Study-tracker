package com.study_tracker.ayush.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String STUDENT_ROLE = "STUDENT";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String ADMIN_SUBJECT = "admin";

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                Claims claims = jwtService.validateAndExtractClaims(token);

                String subject = claims.getSubject();
                String role = claims.get("role", String.class);

                boolean validStudent =
                        STUDENT_ROLE.equals(role)
                                && subject != null
                                && subject.matches("[1-9]\\d*");

                boolean validAdmin =
                        ADMIN_ROLE.equals(role)
                                && ADMIN_SUBJECT.equals(subject);

                if (validStudent || validAdmin) {

                    var authentication =
                            new UsernamePasswordAuthenticationToken(
                                    subject,
                                    null,
                                    List.of(
                                            new SimpleGrantedAuthority("ROLE_" + role)
                                    )
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                } else {
                    SecurityContextHolder.clearContext();
                }

            } catch (JwtException | IllegalArgumentException exception) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}