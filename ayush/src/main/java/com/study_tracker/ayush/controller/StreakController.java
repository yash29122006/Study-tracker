package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.dto.StreakResponse;
import com.study_tracker.ayush.service.StreakService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students/{studentId}/streak")
public class StreakController {

    private final StreakService streakService;

    public StreakController(StreakService streakService) {
        this.streakService = streakService;
    }

    @GetMapping
    public StreakResponse getStreak(
            @PathVariable Long studentId) {

        try {
            return streakService.calculateStreak(studentId);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }
}