package com.example.eduops;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Grade implements Serializable {
    private String courseId;
    private String courseName;
    private double gpa;
    private List<ScoreItem> scores;

    public Grade(String courseId, String courseName, double gpa) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.gpa = gpa;
        this.scores = new ArrayList<>();
    }

    public void addScore(ScoreItem score) {
        this.scores.add(score);
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getGpa() {
        return gpa;
    }

    public String getGpaString() {
        if (gpa == 0.0) {
            return "NO GRADE";
        }
        return String.format("%.2f", gpa);
    }

    public List<ScoreItem> getScores() {
        return scores;
    }

    public String getLetterGrade() {
        if (gpa == 0.0) return "N/A";
        if (gpa >= 3.5) return "A";
        if (gpa >= 3.0) return "B+";
        if (gpa >= 2.5) return "B";
        if (gpa >= 2.0) return "C+";
        if (gpa >= 1.5) return "C";
        if (gpa >= 1.0) return "D";
        return "F";
    }

    public int getGpaColor() {
        if (gpa == 0.0) return 0xFF757575;
        if (gpa >= 3.0) return 0xFF2E7D32;
        if (gpa >= 2.0) return 0xFFF57C00;
        return 0xFFC62828; // Red
    }

    public static class ScoreItem implements Serializable {
        private String itemName;
        private String category;
        private double score;
        private double maxScore;

        public ScoreItem(String itemName, String category, double score, double maxScore) {
            this.itemName = itemName;
            this.category = category;
            this.score = score;
            this.maxScore = maxScore;
        }

        public String getItemName() {
            return itemName;
        }

        public String getCategory() {
            return category;
        }

        public double getScore() {
            return score;
        }

        public double getMaxScore() {
            return maxScore;
        }

        public String getScoreString() {
            return String.format("%.0f / %.0f", score, maxScore);
        }

        public double getPercentage() {
            return (score / maxScore) * 100;
        }

        public String getPercentageString() {
            return String.format("%.1f%%", getPercentage());
        }
    }
}
