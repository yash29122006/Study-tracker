package com.study_tracker.ayush.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "target_questions", nullable = false)
    private Integer targetQuestions = 75;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTargetQuestions() {
        return targetQuestions;
    }

    public void setTargetQuestions(Integer targetQuestions) {
        this.targetQuestions = targetQuestions;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}