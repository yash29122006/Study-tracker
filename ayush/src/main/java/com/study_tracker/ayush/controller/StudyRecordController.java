package com.study_tracker.ayush.controller;

import com.study_tracker.ayush.dto.StudyRecordRequest;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.service.StudyRecordService;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/students/{studentId}/records")
public class StudyRecordController {

    private final StudyRecordService studyRecordService;

    public StudyRecordController(StudyRecordService studyRecordService) {
        this.studyRecordService = studyRecordService;
    }

    @PostMapping
    public StudyRecord saveRecord(
            @PathVariable Long studentId,
            @Valid @RequestBody StudyRecordRequest request) {
        try {
            return studyRecordService.saveDailyRecord(studentId, request);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping
    public List<StudyRecord> getRecords(
            @PathVariable Long studentId) {
        try {
            return studyRecordService.getStudentRecords(studentId);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }
}