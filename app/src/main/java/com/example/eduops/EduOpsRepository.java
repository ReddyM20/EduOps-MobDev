package com.example.eduops;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes all data management to ensure consistency across the application.
 */
public class EduOpsRepository {
    private static EduOpsRepository instance;
    
    private List<Course> courses;
    private List<Grade> grades;
    private List<Assignment> assignments;

    private EduOpsRepository() {
        initializeData();
    }

    public static synchronized EduOpsRepository getInstance() {
        if (instance == null) {
            instance = new EduOpsRepository();
        }
        return instance;
    }

    private void initializeData() {
        // Initialize Courses and Lessons
        courses = new ArrayList<>();
        
        Course mobileDev = new Course("CIS2203N", "MOBILE DEVELOPMENT", "Paule Glenn Acuin",
                "Learn Android app development with Java, UI design, and mobile best practices.");
        mobileDev.addLesson(new Lesson("L1", "Introduction to Android", "Overview of Android platform", Lesson.ContentType.TEXT));
        mobileDev.addLesson(new Lesson("L2", "Activities and Intents", "Understanding the building blocks", Lesson.ContentType.VIDEO));
        courses.add(mobileDev);

        Course nciv = new Course("CIS2206N", "NC IV CERTIFICATE 2", "John Carlo",
                "Advanced certification covering system integration and network administration.");
        courses.add(nciv);

        Course dsa = new Course("CIS2201", "DATA STRUCTURES AND ALGORITHMS", "Jane Carla",
                "Master fundamental data structures and computational problem-solving techniques.");
        courses.add(dsa);

        // Initialize Assignments
        assignments = new ArrayList<>();
        assignments.add(new Assignment("A1", "Coding Activity", "Data Structures and Algorithms",
                "Due March 25, 2026, 11:59 PM", "Complete coding on loops.", false));
        assignments.add(new Assignment("A2", "Mock Test", "NC IV",
                "Due March 29, 2026, 11:59 PM", "Complete the mock test.", false));
        assignments.add(new Assignment("A3", "Do It Yourself", "Mobile Development",
                "Due April 5, 2026, 11:59 PM", "Create a simple mobile app.", false));

        // Initialize Grades
        grades = new ArrayList<>();
        Grade dsaGrade = new Grade("CIS2201", "DATA STRUCTURES AND ALGORITHMS", 3.75);
        dsaGrade.addScore(new Grade.ScoreItem("Midterm Exam", "Exam", 85, 100));
        grades.add(dsaGrade);
        
        Grade mobileGrade = new Grade("CIS2203N", "MOBILE DEVELOPMENT", 3.25);
        mobileGrade.addScore(new Grade.ScoreItem("Quiz 1", "Quiz", 25, 30));
        grades.add(mobileGrade);
    }

    public List<Course> getCourses() { return courses; }
    public List<Grade> getGrades() { return grades; }
    public List<Assignment> getAssignments() { return assignments; }
    
    public void updateAssignment(Assignment updated) {
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getAssignmentId().equals(updated.getAssignmentId())) {
                assignments.set(i, updated);
                break;
            }
        }
    }
}
