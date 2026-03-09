package com.example.eduops;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AssignmentsActivity extends AppCompatActivity {

    // { title, subject, dueDate, priority, isCompleted }
    private static final Object[][] ALL_ASSIGNMENTS = {
            {"Math Homework",    "Mathematics", "Due March 2, 2026, 11:59 PM", "High",   false},
            {"English Homework", "English",     "Due March 2, 2026, 11:59 PM", "Medium", false},
            {"Science Homework", "Science",     "Due March 2, 2026, 11:59 PM", "High",   false},
    };

    private String currentFilter = "All";

    private TextView filterAll;
    private TextView filterUpcoming;
    private TextView filterCompleted;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        filterAll       = findViewById(R.id.filterAll);
        filterUpcoming  = findViewById(R.id.filterUpcoming);
        filterCompleted = findViewById(R.id.filterCompleted);
        container       = findViewById(R.id.assignmentsContainer);

        filterAll.setOnClickListener(v       -> applyFilter("All"));
        filterUpcoming.setOnClickListener(v  -> applyFilter("Upcoming"));
        filterCompleted.setOnClickListener(v -> applyFilter("Completed"));

        setupNavigation();
        applyFilter("All");
    }

    private void applyFilter(String filter) {
        currentFilter = filter;
        updateFilterTabs();
        populateList();
    }

    private void updateFilterTabs() {
        setTabSelected(filterAll,       "All".equals(currentFilter));
        setTabSelected(filterUpcoming,  "Upcoming".equals(currentFilter));
        setTabSelected(filterCompleted, "Completed".equals(currentFilter));
    }

    private void setTabSelected(TextView tab, boolean selected) {
        if (selected) {
            tab.setBackgroundResource(R.drawable.filter_tab_selected_bg);
            tab.setTextColor(getResources().getColor(android.R.color.white, null));
            tab.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tab.setBackgroundResource(R.drawable.filter_tab_unselected_bg);
            tab.setTextColor(getResources().getColor(R.color.text_primary, null));
            tab.setTypeface(null, android.graphics.Typeface.NORMAL);
        }
    }

    private void populateList() {
        container.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Object[] a : ALL_ASSIGNMENTS) {
            boolean isCompleted = (boolean) a[4];
            if ("Upcoming".equals(currentFilter)  &&  isCompleted) continue;
            if ("Completed".equals(currentFilter) && !isCompleted) continue;

            View item = inflater.inflate(R.layout.item_assignment, container, false);

            ((TextView) item.findViewById(R.id.assignmentTitle)).setText((String) a[0]);
            ((TextView) item.findViewById(R.id.assignmentSubject)).setText((String) a[1]);
            ((TextView) item.findViewById(R.id.dueDate)).setText((String) a[2]);

            TextView badge = item.findViewById(R.id.priorityBadge);
            String priority = (String) a[3];
            badge.setText(priority + " Priority");
            badge.setBackgroundResource("High".equals(priority)
                    ? R.drawable.priority_high_bg
                    : R.drawable.priority_medium_bg);

            container.addView(item);
        }
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            Intent i = new Intent(this, DashboardActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        findViewById(R.id.navAnnouncements).setOnClickListener(v -> {
            Intent i = new Intent(this, AnnouncementsActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });

        findViewById(R.id.navAssignments).setOnClickListener(v -> { /* already here */ });

        findViewById(R.id.navGrades).setOnClickListener(v ->
                startActivity(new Intent(this, GradesActivity.class)));
    }
}
