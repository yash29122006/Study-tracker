package com.study_tracker.ayush.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PinAuthService {

    private static final Long STUDENT_ID = 1L;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final String studentPinHash;
    private final String adminPinHash;

    public PinAuthService(
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            @Value("${app.auth.student-pin-hash}") String studentPinHash,
            @Value("${app.auth.admin-pin-hash}") String adminPinHash) {

        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.studentPinHash = studentPinHash;
        this.adminPinHash = adminPinHash;
    }

    public String authenticateStudent(String pin) {
        if (!isValidPinFormat(pin)
                || !passwordEncoder.matches(pin, studentPinHash)) {
            throw new InvalidPinException("Invalid PIN");
        }

        return jwtService.generateToken(
                String.valueOf(STUDENT_ID),
                "STUDENT"
        );
    }

    public String authenticateAdmin(String pin) {
        if (!isValidPinFormat(pin)
                || !passwordEncoder.matches(pin, adminPinHash)) {
            throw new InvalidPinException("Invalid PIN");
        }

        return jwtService.generateToken("admin", "ADMIN");
    }

    private boolean isValidPinFormat(String pin) {
        return pin != null && pin.matches("\\d{4}");
    }

    public static class InvalidPinException extends RuntimeException {
        public InvalidPinException(String message) {
            super(message);
        }
    }
}