package com.study_tracker.ayush.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class StudentOwnershipAuthorizationManager
        implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(
            Supplier<Authentication> authenticationSupplier,
            RequestAuthorizationContext context) {

        Authentication authentication = authenticationSupplier.get();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ANONYMOUS"))) {
            return new AuthorizationDecision(false);
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return new AuthorizationDecision(true);
        }

        boolean isStudent = authentication.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_STUDENT"));

        String requestedStudentId =
                context.getVariables().get("studentId");

        boolean ownsResource =
                isStudent
                        && requestedStudentId != null
                        && requestedStudentId.equals(authentication.getName());

        return new AuthorizationDecision(ownsResource);
    }
}