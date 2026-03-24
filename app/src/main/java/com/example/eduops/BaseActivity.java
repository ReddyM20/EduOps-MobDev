package com.example.eduops;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected EduOpsRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = EduOpsRepository.getInstance();
    }

    protected void setupCommonNavigation() {
        View navDashboard = findViewById(R.id.navDashboard);
        View navAnnouncements = findViewById(R.id.navAnnouncements);
        View navAssignments = findViewById(R.id.navAssignments);
        View navGrades = findViewById(R.id.navGrades);

        if (navDashboard != null) {
            navDashboard.setOnClickListener(v -> navigateTo(DashboardActivity.class));
        }
        if (navAnnouncements != null) {
            navAnnouncements.setOnClickListener(v -> navigateTo(AnnouncementsActivity.class));
        }
        if (navAssignments != null) {
            navAssignments.setOnClickListener(v -> navigateTo(AssignmentsActivity.class));
        }
        if (navGrades != null) {
            navGrades.setOnClickListener(v -> navigateTo(GradesActivity.class));
        }
    }

    private void navigateTo(Class<?> targetActivity) {
        if (this.getClass().equals(targetActivity)) {
            return;
        }
        Intent intent = new Intent(this, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0); // Remove flicker
    }
}
