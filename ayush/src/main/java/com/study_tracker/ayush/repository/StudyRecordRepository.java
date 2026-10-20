package com.study_tracker.ayush.repository;

import com.study_tracker.ayush.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudyRecordRepository
        extends JpaRepository<StudyRecord, Long> {

    Optional<StudyRecord> findByStudent_IdAndStudyDate(
            Long studentId,
            LocalDate studyDate
    );

    List<StudyRecord> findByStudent_IdOrderByStudyDateDesc(
            Long studentId
    );
}