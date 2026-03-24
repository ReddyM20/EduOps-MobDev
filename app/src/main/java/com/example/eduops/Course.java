package com.example.eduops;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String courseId;
    private String courseName;
    private String instructor;
    private String description;
    private List<Lesson> lessons;

    public Course(String courseId, String courseName, String instructor, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instructor = instructor;
        this.description = description;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}
