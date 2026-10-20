package com.study_tracker.ayush.dto;

public class MonthlyProgressResponse {

    private int year;
    private int month;
    private long totalPhysicsQuestions;
    private long totalChemistryQuestions;
    private long totalMathematicsQuestions;
    private long totalQuestions;
    private long daysStudied;
    private long targetDays;
    private int targetQuestions;

    public MonthlyProgressResponse(
            int year,
            int month,
            long totalPhysicsQuestions,
            long totalChemistryQuestions,
            long totalMathematicsQuestions,
            long totalQuestions,
            long daysStudied,
            long targetDays,
            int targetQuestions) {

        this.year = year;
        this.month = month;
        this.totalPhysicsQuestions = totalPhysicsQuestions;
        this.totalChemistryQuestions = totalChemistryQuestions;
        this.totalMathematicsQuestions = totalMathematicsQuestions;
        this.totalQuestions = totalQuestions;
        this.daysStudied = daysStudied;
        this.targetDays = targetDays;
        this.targetQuestions = targetQuestions;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public long getTotalPhysicsQuestions() {
        return totalPhysicsQuestions;
    }

    public long getTotalChemistryQuestions() {
        return totalChemistryQuestions;
    }

    public long getTotalMathematicsQuestions() {
        return totalMathematicsQuestions;
    }

    public long getTotalQuestions() {
        return totalQuestions;
    }

    public long getDaysStudied() {
        return daysStudied;
    }

    public long getTargetDays() {
        return targetDays;
    }

    public int getTargetQuestions() {
        return targetQuestions;
    }
}