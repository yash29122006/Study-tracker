package com.study_tracker.ayush.dto;

import java.time.LocalDate;

public class DashboardResponse {

    private Long studentId;
    private String studentName;
    private LocalDate studyDate;

    private int physicsQuestions;
    private int chemistryQuestions;
    private int mathematicsQuestions;
    private int totalQuestions;

    private int targetQuestions;
    private double targetPercentage;
    private boolean targetAchieved;

    public DashboardResponse(
            Long studentId,
            String studentName,
            LocalDate studyDate,
            int physicsQuestions,
            int chemistryQuestions,
            int mathematicsQuestions,
            int targetQuestions) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.studyDate = studyDate;

        this.physicsQuestions = physicsQuestions;
        this.chemistryQuestions = chemistryQuestions;
        this.mathematicsQuestions = mathematicsQuestions;

        this.totalQuestions =
                physicsQuestions + chemistryQuestions + mathematicsQuestions;

        this.targetQuestions = targetQuestions;

        this.targetPercentage = targetQuestions > 0
                ? Math.min(100.0,
                (totalQuestions * 100.0) / targetQuestions)
                : 0.0;

        this.targetAchieved = totalQuestions >= targetQuestions;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
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

    public double getTargetPercentage() {
        return targetPercentage;
    }

    public boolean isTargetAchieved() {
        return targetAchieved;
    }
}