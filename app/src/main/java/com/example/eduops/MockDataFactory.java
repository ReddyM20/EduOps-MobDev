package com.example.eduops;

import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes data initialization to reduce duplication across activities.
 */
public class MockDataFactory {

    private static List<Course> cachedCourses;
    private static List<Assignment> cachedAssignments;
    private static List<Grade> cachedGrades;

    /**
     * Get courses list (cached after first initialization)
     */
    public static List<Course> getCourses() {
        if (cachedCourses == null) {
            cachedCourses = createCourses();
        }
        return cachedCourses;
    }


    /**
     * Get assignments list (cached after first initialization)
     */
    public static List<Assignment> getAssignments() {
        if (cachedAssignments == null) {
            cachedAssignments = createAssignments();
        }
        return cachedAssignments;
    }

    /**
     * Get grades list (cached after first initialization)
     */
    public static List<Grade> getGrades() {
        if (cachedGrades == null) {
            cachedGrades = createGrades();
        }
        return cachedGrades;
    }

    private static List<Course> createCourses() {
        List<Course> courses = new ArrayList<>();

        // Course 1: Mobile Development
        Course mobileDev = new Course("CIS2203N", "MOBILE DEVELOPMENT", "Paule Glenn Acuin",
                "Learn Android app development with Java, UI design, and mobile best practices.");

        Lesson lesson1 = new Lesson("L1", "Introduction to Android",
                "Overview of Android platform and development environment", Lesson.ContentType.TEXT);
        lesson1.setTextContent("Android is an open-source mobile operating system developed by Google. " +
                "It powers billions of devices worldwide and provides developers with powerful tools to create innovative applications.");
        lesson1.setDurationMinutes(20);

        Lesson lesson2 = new Lesson("L2", "Activities and Intents",
                "Understanding the building blocks of Android apps", Lesson.ContentType.VIDEO);
        lesson2.setContentUrl("https://www.youtube.com/watch?v=example1");
        lesson2.setDurationMinutes(45);

        Lesson lesson3 = new Lesson("L3", "Android Project Structure",
                "Detailed guide on Android project organization", Lesson.ContentType.PDF);
        lesson3.setContentUrl("https://developer.android.com/guide");
        lesson3.setDurationMinutes(30);

        mobileDev.addLesson(lesson1);
        mobileDev.addLesson(lesson2);
        mobileDev.addLesson(lesson3);
        courses.add(mobileDev);

        // Course 2: NC IV Certificate 2
        Course nciv = new Course("CIS2206N", "NC IV CERTIFICATE 2", "John Carlo",
                "Advanced certification covering system integration and network administration.");

        Lesson ncLesson1 = new Lesson("N1", "Network Fundamentals",
                "Basic networking concepts and protocols", Lesson.ContentType.TEXT);
        ncLesson1.setTextContent("Computer networks enable communication between devices. " +
                "Key concepts include IP addressing, routing, DNS, and network security.");
        ncLesson1.setDurationMinutes(25);

        Lesson ncLesson2 = new Lesson("N2", "System Administration",
                "Managing servers and services", Lesson.ContentType.VIDEO);
        ncLesson2.setContentUrl("https://www.youtube.com/watch?v=example2");
        ncLesson2.setDurationMinutes(50);

        nciv.addLesson(ncLesson1);
        nciv.addLesson(ncLesson2);
        courses.add(nciv);

        // Course 3: Data Structures and Algorithms
        Course dsa = new Course("CIS2201", "DATA STRUCTURES AND ALGORITHMS", "Jane Carla",
                "Master fundamental data structures, algorithms, and computational problem-solving techniques.");

        Lesson dsaLesson1 = new Lesson("D1", "Arrays and Linked Lists",
                "Understanding linear data structures", Lesson.ContentType.TEXT);
        dsaLesson1.setTextContent("Arrays store elements in contiguous memory, offering O(1) access time. " +
                "Linked lists use pointers, providing flexible insertion and deletion.");
        dsaLesson1.setDurationMinutes(35);

        Lesson dsaLesson2 = new Lesson("D2", "Sorting Algorithms",
                "Comparison of different sorting techniques", Lesson.ContentType.VIDEO);
        dsaLesson2.setContentUrl("https://www.youtube.com/watch?v=example3");
        dsaLesson2.setDurationMinutes(40);

        Lesson dsaLesson3 = new Lesson("D3", "Algorithm Analysis",
                "Time and space complexity reference", Lesson.ContentType.PDF);
        dsaLesson3.setContentUrl("https://www.bigocheatsheet.com");
        dsaLesson3.setDurationMinutes(25);

        dsa.addLesson(dsaLesson1);
        dsa.addLesson(dsaLesson2);
        dsa.addLesson(dsaLesson3);
        courses.add(dsa);

        return courses;
    }

    private static List<Assignment> createAssignments() {
        List<Assignment> assignments = new ArrayList<>();

        assignments.add(new Assignment("CIS2201", "Coding Activity", "Mathematics",
                "Due March 25, 2026, 11:59 PM",
                "Complete the coding activity on loops and functions. Submit your code files and a brief explanation of your approach.",
                false));

        assignments.add(new Assignment("CIS2206N", "Mock Test", "NC IV",
                "Due March 29, 2026, 11:59 PM",
                "Complete the mock test covering all topics discussed in class. Focus on problem-solving techniques and time management.",
                false));

        assignments.add(new Assignment("CIS2203N", "Do It Yourself", "Mobile Development",
                "Due April 5, 2026, 11:59 PM",
                "Create a simple mobile app that displays a list of items and allows users to add, edit, and delete items.",
                false));

        return assignments;
    }

    private static List<Grade> createGrades() {
        List<Grade> grades = new ArrayList<>();

        // Grade 1: Data Structures and Algorithms - High GPA
        Grade dsa = new Grade("CIS2201", "DATA STRUCTURES AND ALGORITHMS", 3.75);
        dsa.addScore(new Grade.ScoreItem("Exercise 1: Arrays", "Exercise", 48, 50));
        dsa.addScore(new Grade.ScoreItem("Exercise 2: Linked Lists", "Exercise", 45, 50));
        dsa.addScore(new Grade.ScoreItem("Quiz 1", "Quiz", 28, 30));
        dsa.addScore(new Grade.ScoreItem("Midterm Exam", "Exam", 85, 100));
        dsa.addScore(new Grade.ScoreItem("Final Project", "Project", 92, 100));
        grades.add(dsa);

        // Grade 2: Mobile Development - Medium GPA
        Grade mobileDev = new Grade("CIS2203N", "MOBILE DEVELOPMENT", 3.25);
        mobileDev.addScore(new Grade.ScoreItem("Coding Activity 1", "Exercise", 40, 50));
        mobileDev.addScore(new Grade.ScoreItem("Coding Activity 2", "Exercise", 42, 50));
        mobileDev.addScore(new Grade.ScoreItem("Quiz 1", "Quiz", 25, 30));
        mobileDev.addScore(new Grade.ScoreItem("Midterm Exam", "Exam", 78, 100));
        mobileDev.addScore(new Grade.ScoreItem("Do It Yourself Project", "Project", 88, 100));
        grades.add(mobileDev);

        // Grade 3: NC IV Certificate - No Grade Yet
        Grade nciv = new Grade("CIS2206N", "NC IV CERTIFICATE 2", 0.0);
        nciv.addScore(new Grade.ScoreItem("Mock Test 1", "Exercise", 35, 50));
        nciv.addScore(new Grade.ScoreItem("Lab Activity 1", "Exercise", 45, 50));
        grades.add(nciv);

        return grades;
    }
}
