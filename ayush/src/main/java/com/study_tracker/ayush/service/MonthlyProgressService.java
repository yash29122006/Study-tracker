package com.study_tracker.ayush.service;

import com.study_tracker.ayush.dto.MonthlyProgressResponse;
import com.study_tracker.ayush.entity.Student;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.repository.StudentRepository;
import com.study_tracker.ayush.repository.StudyRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonthlyProgressService {

    private final StudentRepository studentRepository;
    private final StudyRecordRepository studyRecordRepository;

    public MonthlyProgressService(
            StudentRepository studentRepository,
            StudyRecordRepository studyRecordRepository) {
        this.studentRepository = studentRepository;
        this.studyRecordRepository = studyRecordRepository;
    }

    public MonthlyProgressResponse getMonthlyProgress(
            Long studentId, int year, int month) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(
                    "Month must be between 1 and 12");
        }

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<StudyRecord> records =
                studyRecordRepository
                        .findByStudent_IdOrderByStudyDateDesc(studentId)
                        .stream()
                        .filter(record ->
                                !record.getStudyDate().isBefore(startDate)
                                        && !record.getStudyDate().isAfter(endDate))
                        .toList();

        long physics = 0;
        long chemistry = 0;
        long mathematics = 0;
        long daysStudied = 0;
        long targetDays = 0;

        for (StudyRecord record : records) {

            physics += record.getPhysicsQuestions();
            chemistry += record.getChemistryQuestions();
            mathematics += record.getMathematicsQuestions();

            int dailyTotal = record.getTotalQuestions();

            if (dailyTotal > 0) {
                daysStudied++;
            }

            if (dailyTotal >= student.getTargetQuestions()) {
                targetDays++;
            }
        }

        long total = physics + chemistry + mathematics;

        return new MonthlyProgressResponse(
                year,
                month,
                physics,
                chemistry,
                mathematics,
                total,
                daysStudied,
                targetDays,
                student.getTargetQuestions()
        );
    }
}