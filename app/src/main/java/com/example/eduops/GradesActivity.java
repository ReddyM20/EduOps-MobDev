package com.example.eduops;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GradesActivity extends AppCompatActivity {

    // { courseCode, status }  — status: "PASS" | "FAIL" | "NO GRADE"
    private static final String[][] GRADES = {
            {"CIS 2101",  "NO GRADE"},
            {"CIS 2103N", "PASS"},
            {"CIS 2106N", "FAIL"},
            {"CIS 4210N", "PASS"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        populateGrades();
        setupNavigation();
    }

    private void populateGrades() {
        LinearLayout container = findViewById(R.id.gradesContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String[] grade : GRADES) {
            View row = inflater.inflate(R.layout.item_grade_row, container, false);

            ((TextView) row.findViewById(R.id.gradeCourseName)).setText(grade[0]);

            TextView statusView = row.findViewById(R.id.gradeStatus);
            statusView.setText(grade[1]);
            switch (grade[1]) {
                case "PASS":
                    statusView.setTextColor(Color.parseColor("#2E7D32"));
                    break;
                case "FAIL":
                    statusView.setTextColor(Color.parseColor("#C62828"));
                    break;
                default:
                    statusView.setTextColor(Color.parseColor("#757575"));
                    break;
            }

            row.findViewById(R.id.detailsButton).setOnClickListener(v ->
                    Toast.makeText(this, "Details for " + grade[0], Toast.LENGTH_SHORT).show());

            container.addView(row);
        }
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navAnnouncements).setOnClickListener(v -> {
            Intent intent = new Intent(this, AnnouncementsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navAssignments).setOnClickListener(v -> {
            Intent intent = new Intent(this, AssignmentsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navGrades).setOnClickListener(v -> { /* already here */ });
    }
}
