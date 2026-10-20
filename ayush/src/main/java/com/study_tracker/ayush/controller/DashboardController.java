package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.dto.DashboardResponse;
import com.study_tracker.ayush.service.DashboardService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/students/{studentId}/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard(
            @PathVariable Long studentId,
            @RequestParam(required = false) LocalDate date) {

        try {
            LocalDate requestedDate = date != null
                    ? date
                    : dashboardService.getTodayInIndia();

            return dashboardService.getDailyDashboard(
                    studentId,
                    requestedDate
            );

        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exception.getMessage()
            );
        }
    }
}