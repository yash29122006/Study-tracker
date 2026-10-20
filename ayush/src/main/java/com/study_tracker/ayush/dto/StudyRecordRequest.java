package com.study_tracker.ayush.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StudyRecordRequest {

    @NotNull
    private LocalDate studyDate;

    @NotNull
    @Min(0)
    private Integer physicsQuestions;

    @NotNull
    @Min(0)
    private Integer chemistryQuestions;

    @NotNull
    @Min(0)
    private Integer mathematicsQuestions;

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
}