package com.study_tracker.ayush.service;

import com.study_tracker.ayush.dto.StreakResponse;
import com.study_tracker.ayush.entity.Student;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.repository.StudentRepository;
import com.study_tracker.ayush.repository.StudyRecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StreakService {

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    private final StudentRepository studentRepository;
    private final StudyRecordRepository studyRecordRepository;

    public StreakService(
            StudentRepository studentRepository,
            StudyRecordRepository studyRecordRepository) {
        this.studentRepository = studentRepository;
        this.studyRecordRepository = studyRecordRepository;
    }

    @Transactional(readOnly = true)
    public StreakResponse calculateStreak(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        int target = student.getTargetQuestions();

        List<StudyRecord> records =
                studyRecordRepository
                        .findByStudent_IdOrderByStudyDateDesc(studentId);

        Map<LocalDate, Integer> totals = new HashMap<>();

        for (StudyRecord record : records) {
            int total = record.getPhysicsQuestions()
                    + record.getChemistryQuestions()
                    + record.getMathematicsQuestions();

            totals.put(record.getStudyDate(), total);
        }

        LocalDate today = LocalDate.now(INDIA_ZONE);

        // Calculate the current streak.
        int currentStreak = 0;
        LocalDate dateToCheck = today;

        // Allow the streak to remain active if yesterday
        // was the most recent successful day.
        if (totals.getOrDefault(today, 0) < target) {
            dateToCheck = today.minusDays(1);
        }

        while (totals.getOrDefault(dateToCheck, 0) >= target) {
            currentStreak++;
            dateToCheck = dateToCheck.minusDays(1);
        }

        // Calculate the longest historical streak.
        int longestStreak = 0;
        int runningStreak = 0;
        LocalDate previousDate = null;

        for (StudyRecord record : records) {
            LocalDate recordDate = record.getStudyDate();

            int total = totals.getOrDefault(recordDate, 0);

            if (total >= target) {
                if (previousDate != null
                        && previousDate.equals(recordDate.plusDays(1))) {
                    runningStreak++;
                } else {
                    runningStreak = 1;
                }

                longestStreak = Math.max(longestStreak, runningStreak);
            } else {
                runningStreak = 0;
            }

            previousDate = recordDate;
        }

        return new StreakResponse(
                student.getId(),
                student.getName(),
                currentStreak,
                longestStreak,
                target
        );
    }
}