package com.example.eduops;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Assignment implements Serializable {
    private String assignmentId;
    private String title;
    private String subject;
    private String dueDate;
    private String description;
    private Date dueDateParsed;
    private boolean isCompleted;

    // Submission fields
    private boolean isSubmitted;
    private String submittedFileName;
    private String submissionDate;
    private String teacherFeedback;
    private Integer grade;

    public Assignment(String assignmentId, String title, String subject, String dueDate, String description, boolean isCompleted) {
        this.assignmentId = assignmentId;
        this.title = title;
        this.subject = subject;
        this.dueDate = dueDate;
        this.description = description;
        this.isCompleted = isCompleted;
        this.dueDateParsed = parseDueDate(dueDate);
        this.isSubmitted = false;
    }

    private Date parseDueDate(String dueDateStr) {
        try {
            // Parse "Due March 2, 2026, 11:59 PM" format
            String dateOnly = dueDateStr.replace("Due ", "");
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy, hh:mm a", Locale.ENGLISH);
            return sdf.parse(dateOnly);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public String getPriority() {
        if (dueDateParsed == null) {
            return "Medium";
        }

        Date now = new Date();
        long diffInMillis = dueDateParsed.getTime() - now.getTime();
        long daysUntilDue = TimeUnit.MILLISECONDS.toDays(diffInMillis);

        if (daysUntilDue <= 3) {
            return "High";
        } else if (daysUntilDue <= 7) {
            return "Medium";
        } else {
            return "Low";
        }
    }

    // Submission methods
    public void submitAssignment(String fileName) {
        this.isSubmitted = true;
        this.submittedFileName = fileName;
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy, hh:mm a", Locale.ENGLISH);
        this.submissionDate = sdf.format(new Date());
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public String getSubmittedFileName() {
        return submittedFileName;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getTeacherFeedback() {
        return teacherFeedback;
    }

    public void setTeacherFeedback(String teacherFeedback) {
        this.teacherFeedback = teacherFeedback;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getSubmissionStatus() {
        if (!isSubmitted) {
            return "Not Submitted";
        } else if (grade != null) {
            return "Graded";
        } else {
            return "Submitted";
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
