package com.study_tracker.ayush.repository;

import com.study_tracker.ayush.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}