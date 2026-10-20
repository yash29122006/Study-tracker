package com.study_tracker.ayush.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PinAuthService pinAuthService;

    public AuthController(PinAuthService pinAuthService) {
        this.pinAuthService = pinAuthService;
    }

    @PostMapping("/student")
    public AuthResponse studentLogin(@RequestBody PinRequest request) {
        String token = pinAuthService.authenticateStudent(request.pin());
        return new AuthResponse(token, "STUDENT");
    }

    @PostMapping("/admin")
    public AuthResponse adminLogin(@RequestBody PinRequest request) {
        String token = pinAuthService.authenticateAdmin(request.pin());
        return new AuthResponse(token, "ADMIN");
    }

    public record PinRequest(String pin) {}

    public record AuthResponse(String token, String role) {}
}