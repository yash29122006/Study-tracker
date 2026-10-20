package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.dto.WeeklyProgressResponse;
import com.study_tracker.ayush.service.WeeklyProgressService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/students/{studentId}/weekly-progress")
public class WeeklyProgressController {

    private final WeeklyProgressService weeklyProgressService;

    public WeeklyProgressController(
            WeeklyProgressService weeklyProgressService) {
        this.weeklyProgressService = weeklyProgressService;
    }

    @GetMapping
    public List<WeeklyProgressResponse> getWeeklyProgress(
            @PathVariable Long studentId) {

        try {
            return weeklyProgressService.getWeeklyProgress(studentId);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }
}