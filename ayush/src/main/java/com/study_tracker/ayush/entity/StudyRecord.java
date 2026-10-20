package com.study_tracker.ayush.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
        name = "study_records",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_student_study_date",
                        columnNames = {"student_id", "study_date"}
                )
        }
)
public class StudyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "study_date", nullable = false)
    private LocalDate studyDate;

    @Column(name = "physics_questions", nullable = false)
    private Integer physicsQuestions = 0;

    @Column(name = "chemistry_questions", nullable = false)
    private Integer chemistryQuestions = 0;

    @Column(name = "mathematics_questions", nullable = false)
    private Integer mathematicsQuestions = 0;

    @Column(
            name = "created_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private OffsetDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private OffsetDateTime updatedAt;

    public StudyRecord() {
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(LocalDate studyDate) {
        this.studyDate = studyDate;
    }

    public Integer getPhysicsQuestions() {
        return physicsQuestions;
    }

    public void setPhysicsQuestions(Integer physicsQuestions) {
        this.physicsQuestions = physicsQuestions;
    }

    public Integer getChemistryQuestions() {
        return chemistryQuestions;
    }

    public void setChemistryQuestions(Integer chemistryQuestions) {
        this.chemistryQuestions = chemistryQuestions;
    }

    public Integer getMathematicsQuestions() {
        return mathematicsQuestions;
    }

    public void setMathematicsQuestions(Integer mathematicsQuestions) {
        this.mathematicsQuestions = mathematicsQuestions;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getTotalQuestions() {
        return physicsQuestions + chemistryQuestions + mathematicsQuestions;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}