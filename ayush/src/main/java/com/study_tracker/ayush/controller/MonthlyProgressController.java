package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.dto.MonthlyProgressResponse;
import com.study_tracker.ayush.service.MonthlyProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/students/{studentId}/monthly-progress")
public class MonthlyProgressController {

    private final MonthlyProgressService monthlyProgressService;

    public MonthlyProgressController(
            MonthlyProgressService monthlyProgressService) {
        this.monthlyProgressService = monthlyProgressService;
    }

    @GetMapping
    public MonthlyProgressResponse getMonthlyProgress(
            @PathVariable Long studentId,
            @RequestParam int year,
            @RequestParam int month) {

        try {
            return monthlyProgressService.getMonthlyProgress(
                    studentId, year, month);
        } catch (IllegalArgumentException exception) {
            HttpStatus status = exception.getMessage().equals(
                    "Student not found")
                    ? HttpStatus.NOT_FOUND
                    : HttpStatus.BAD_REQUEST;

            throw new ResponseStatusException(
                    status, exception.getMessage());
        }
    }
}