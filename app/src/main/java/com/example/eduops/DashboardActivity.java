package com.example.eduops;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    // Course groups: { groupTitle, semester, courseCode, progressPercent }
    private static final Object[][] COURSE_GROUPS = {
            {"MOBILE DEVELOPMENT", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2203N - January 2026", 75},
            {"NC IV CERTIFICATE 2", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2206N - January 2026", 75},
            {"DATA STRUCTURES AND\nALGORITHMS", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2201 - January 2026", 75},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        populateCourses();
        setupNavigation();
    }

    private void populateCourses() {
        LinearLayout container = findViewById(R.id.coursesContainer);
        if (container == null) return;
        
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Object[] group : COURSE_GROUPS) {
            String groupTitle = (String) group[0];
            String semester = (String) group[1];
            String courseCode = (String) group[2];
            int progress = (int) group[3];

            View item = inflater.inflate(R.layout.view_course_group, container, false);

            TextView titleView = item.findViewById(R.id.groupTitle);
            TextView semesterView = item.findViewById(R.id.groupSemester);
            TextView codeView = item.findViewById(R.id.courseCode);
            ProgressBar progressBar = item.findViewById(R.id.progressBar);
            TextView percentView = item.findViewById(R.id.progressPercent);
            TextView labelView = item.findViewById(R.id.progressLabel);

            titleView.setText(groupTitle);
            semesterView.setText(semester);
            codeView.setText(courseCode);
            progressBar.setProgress(progress);
            percentView.setText(progress + "%");
            labelView.setText("Progress: " + progress + "%");

            container.addView(item);
        }
    }

    private void setupNavigation() {
        View navDashboard = findViewById(R.id.navDashboard);
        View navAnnouncements = findViewById(R.id.navAnnouncements);
        View navAssignments = findViewById(R.id.navAssignments);
        View navGrades = findViewById(R.id.navGrades);

        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> Toast.makeText(this, "Dashboard Clicked", Toast.LENGTH_SHORT).show());
        }
        if (navAnnouncements != null) {
            navAnnouncements.setOnClickListener(v -> startActivity(
                    new Intent(this, AnnouncementsActivity.class)));
        }
        if (navAssignments != null) {
            navAssignments.setOnClickListener(v -> startActivity(
                    new Intent(this, AssignmentsActivity.class)));
        }
        if (navGrades != null) {
            navGrades.setOnClickListener(v -> startActivity(
                    new Intent(this, GradesActivity.class)));
        }
    }
}
