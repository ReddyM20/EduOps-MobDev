package com.example.eduops;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeCourses();

        ListView listView = findViewById(R.id.coursesListView);
        if (listView != null) {
            listView.setAdapter(new CourseAdapter(this, courses));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Course selectedCourse = courses.get(position);
                Intent intent = new Intent(this, CourseDetailActivity.class);
                intent.putExtra("course", selectedCourse);
                startActivity(intent);
            });
        }

        setupNavigation();
    }

    private void initializeCourses() {
        courses = new ArrayList<>();

        // Course 1: Mobile Development
        Course mobileDev = new Course("CIS2203N", "MOBILE DEVELOPMENT", "Prof. Sarah Johnson",
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
        Course nciv = new Course("CIS2206N", "NC IV CERTIFICATE 2", "Prof. Michael Chen",
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
        Course dsa = new Course("CIS2201", "DATA STRUCTURES AND ALGORITHMS", "Dr. Emily Rodriguez",
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
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> { /* already here */ });
        findViewById(R.id.navAnnouncements).setOnClickListener(v -> startActivity(new Intent(this, AnnouncementsActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
        findViewById(R.id.navGrades).setOnClickListener(v -> startActivity(new Intent(this, GradesActivity.class)));
    }

    static class CourseAdapter extends BaseAdapter {
        private final Context context;
        private final List<Course> list;
        private final int[] progressValues = {90, 65, 40}; // Hardcoded progress for display

        CourseAdapter(Context c, List<Course> l) { context=c; list=l; }
        @Override public int getCount() { return list.size(); }
        @Override public Object getItem(int i) { return list.get(i); }
        @Override public long getItemId(int i) { return i; }
        @Override public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = LayoutInflater.from(context).inflate(R.layout.view_course_group, parent, false);
            Course c = list.get(i);
            int progress = (i < progressValues.length) ? progressValues[i] : 0;

            ((TextView) view.findViewById(R.id.groupTitle)).setText(c.getCourseName());
            ((TextView) view.findViewById(R.id.groupSemester)).setText("AY 2025 - 2026, 2ND SEMESTER");
            ((TextView) view.findViewById(R.id.courseCode)).setText(c.getCourseId() + " - January 2026");
            ((ProgressBar) view.findViewById(R.id.progressBar)).setProgress(progress);
            ((TextView) view.findViewById(R.id.progressPercent)).setText(progress + "%");
            ((TextView) view.findViewById(R.id.progressLabel)).setText("Progress: " + progress + "%");
            return view;
        }
    }
}
