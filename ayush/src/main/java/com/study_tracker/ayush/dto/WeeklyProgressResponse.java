package com.study_tracker.ayush.dto;

import java.time.LocalDate;

public class WeeklyProgressResponse {

    private LocalDate studyDate;
    private int physicsQuestions;
    private int chemistryQuestions;
    private int mathematicsQuestions;
    private int totalQuestions;
    private int targetQuestions;
    private boolean targetAchieved;

    public WeeklyProgressResponse(
            LocalDate studyDate,
            int physicsQuestions,
            int chemistryQuestions,
            int mathematicsQuestions,
            int targetQuestions) {

        this.studyDate = studyDate;
        this.physicsQuestions = physicsQuestions;
        this.chemistryQuestions = chemistryQuestions;
        this.mathematicsQuestions = mathematicsQuestions;

        this.totalQuestions =
                physicsQuestions + chemistryQuestions + mathematicsQuestions;

        this.targetQuestions = targetQuestions;
        this.targetAchieved = totalQuestions >= targetQuestions;
    }

    public LocalDate getStudyDate() {
        return studyDate;
    }

    public int getPhysicsQuestions() {
        return physicsQuestions;
    }

    public int getChemistryQuestions() {
        return chemistryQuestions;
    }

    public int getMathematicsQuestions() {
        return mathematicsQuestions;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getTargetQuestions() {
        return targetQuestions;
    }

    public boolean isTargetAchieved() {
        return targetAchieved;
    }
}