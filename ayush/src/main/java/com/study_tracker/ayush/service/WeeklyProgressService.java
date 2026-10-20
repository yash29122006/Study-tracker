package com.study_tracker.ayush.service;

import com.study_tracker.ayush.dto.WeeklyProgressResponse;
import com.study_tracker.ayush.entity.Student;
import com.study_tracker.ayush.entity.StudyRecord;
import com.study_tracker.ayush.repository.StudentRepository;
import com.study_tracker.ayush.repository.StudyRecordRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class WeeklyProgressService {

    private static final ZoneId INDIA_ZONE =
            ZoneId.of("Asia/Kolkata");

    private final StudentRepository studentRepository;
    private final StudyRecordRepository studyRecordRepository;

    public WeeklyProgressService(
            StudentRepository studentRepository,
            StudyRecordRepository studyRecordRepository) {
        this.studentRepository = studentRepository;
        this.studyRecordRepository = studyRecordRepository;
    }

    @Transactional(readOnly = true)
    public List<WeeklyProgressResponse> getWeeklyProgress(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Student not found"));

        LocalDate today = LocalDate.now(INDIA_ZONE);
        LocalDate startDate = today.minusDays(6);

        List<StudyRecord> records =
                studyRecordRepository
                        .findByStudent_IdOrderByStudyDateDesc(studentId);

        Map<LocalDate, StudyRecord> recordsByDate = records.stream()
                .filter(record ->
                        !record.getStudyDate().isBefore(startDate)
                                && !record.getStudyDate().isAfter(today))
                .collect(Collectors.toMap(
                        StudyRecord::getStudyDate,
                        Function.identity()
                ));

        List<WeeklyProgressResponse> result = new ArrayList<>();

        for (LocalDate date = startDate;
             !date.isAfter(today);
             date = date.plusDays(1)) {

            StudyRecord record = recordsByDate.get(date);

            int physics = record == null
                    ? 0 : record.getPhysicsQuestions();

            int chemistry = record == null
                    ? 0 : record.getChemistryQuestions();

            int mathematics = record == null
                    ? 0 : record.getMathematicsQuestions();

            result.add(new WeeklyProgressResponse(
                    date,
                    physics,
                    chemistry,
                    mathematics,
                    student.getTargetQuestions()
            ));
        }

        return result;
    }
}