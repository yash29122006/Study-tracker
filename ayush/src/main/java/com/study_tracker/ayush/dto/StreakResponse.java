package com.study_tracker.ayush.dto;

public class StreakResponse {

    private Long studentId;
    private String studentName;
    private int currentStreak;
    private int longestStreak;
    private int targetQuestions;

    public StreakResponse(
            Long studentId,
            String studentName,
            int currentStreak,
            int longestStreak,
            int targetQuestions) {

        this.studentId = studentId;
        this.studentName = studentName;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.targetQuestions = targetQuestions;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public int getTargetQuestions() {
        return targetQuestions;
    }
}