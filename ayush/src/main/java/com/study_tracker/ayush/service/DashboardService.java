package com.study_tracker.ayush.service;

import com.study_tracker.ayush.dto.DashboardResponse;
import com.study_tracker.ayush.entity.Student;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.repository.StudentRepository;
import com.study_tracker.ayush.repository.StudyRecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class DashboardService {

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    private final StudentRepository studentRepository;
    private final StudyRecordRepository studyRecordRepository;

    public DashboardService(
            StudentRepository studentRepository,
            StudyRecordRepository studyRecordRepository) {
        this.studentRepository = studentRepository;
        this.studyRecordRepository = studyRecordRepository;
    }

    @Transactional(readOnly = true)
    public DashboardResponse getDailyDashboard(
            Long studentId,
            LocalDate date) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        Optional<StudyRecord> optionalRecord =
                studyRecordRepository
                        .findByStudent_IdAndStudyDate(studentId, date);

        int physics = 0;
        int chemistry = 0;
        int mathematics = 0;

        if (optionalRecord.isPresent()) {
            StudyRecord record = optionalRecord.get();

            physics = record.getPhysicsQuestions();
            chemistry = record.getChemistryQuestions();
            mathematics = record.getMathematicsQuestions();
        }

        return new DashboardResponse(
                student.getId(),
                student.getName(),
                date,
                physics,
                chemistry,
                mathematics,
                student.getTargetQuestions()
        );
    }

    public LocalDate getTodayInIndia() {
        return LocalDate.now(INDIA_ZONE);
    }
}