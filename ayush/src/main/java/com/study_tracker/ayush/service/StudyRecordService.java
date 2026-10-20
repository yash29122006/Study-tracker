package com.study_tracker.ayush.service;

import com.study_tracker.ayush.dto.StudyRecordRequest;
import com.study_tracker.ayush.entity.Student;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.repository.StudentRepository;
import com.study_tracker.ayush.repository.StudyRecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class StudyRecordService {

    private final StudyRecordRepository studyRecordRepository;
    private final StudentRepository studentRepository;

    public StudyRecordService(
            StudyRecordRepository studyRecordRepository,
            StudentRepository studentRepository) {
        this.studyRecordRepository = studyRecordRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public StudyRecord saveDailyRecord(
            Long studentId,
            StudyRecordRequest request) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        StudyRecord record = studyRecordRepository
                .findByStudent_IdAndStudyDate(
                        studentId, request.getStudyDate())
                .orElseGet(StudyRecord::new);

        record.setStudent(student);
        record.setStudyDate(request.getStudyDate());
        record.setPhysicsQuestions(request.getPhysicsQuestions());
        record.setChemistryQuestions(request.getChemistryQuestions());
        record.setMathematicsQuestions(request.getMathematicsQuestions());

        if (record.getId() != null) {
            record.setUpdatedAt(
                    OffsetDateTime.now(ZoneId.of("Asia/Kolkata")));
        }

        return studyRecordRepository.save(record);
    }

    public List<StudyRecord> getStudentRecords(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student not found");
        }

        return studyRecordRepository
                .findByStudent_IdOrderByStudyDateDesc(studentId);
    }
}