package com.example.eduops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsActivity extends AppCompatActivity {

    private List<Assignment> allAssignments;

    private String currentFilter = "All";

    private TextView filterAll;
    private TextView filterUpcoming;
    private TextView filterCompleted;
    private LinearLayout container;

    private ActivityResultLauncher<Intent> assignmentDetailLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        setupAssignmentDetailLauncher();
        initializeAssignments();

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

    private void setupAssignmentDetailLauncher() {
        assignmentDetailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Assignment updatedAssignment = (Assignment) result.getData().getSerializableExtra("assignment");
                        int position = result.getData().getIntExtra("position", -1);
                        if (updatedAssignment != null && position >= 0 && position < allAssignments.size()) {
                            allAssignments.set(position, updatedAssignment);
                            populateList();
                        }
                    }
                }
        );
    }

    private void initializeAssignments() {
        allAssignments = new ArrayList<>();
        // Initialize with sample assignments
        // Note: For testing with current date (March 22, 2026):
        allAssignments.add(new Assignment("CIS2201", "Coding Activity", "Mathematics",
                "Due March 25, 2026, 11:59 PM",
                "Complete the coding activity on loops and functions. Submit your code files and a brief explanation of your approach.",
                false));
        allAssignments.add(new Assignment("CIS2206N", "Mock Test", "NC IV",
                "Due March 29, 2026, 11:59 PM",
                "Complete the mock test covering all topics discussed in class. Focus on problem-solving techniques and time management.",
                false));
        allAssignments.add(new Assignment("CIS2203N", "Do It Yourself", "Mobile Development",
                "Due April 5, 2026, 11:59 PM",
                "Create a simple mobile app that displays a list of items and allows users to add, edit, and delete items.",
                false));
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

        for (int i = 0; i < allAssignments.size(); i++) {
            Assignment assignment = allAssignments.get(i);

            boolean isCompleted = assignment.isCompleted();
            if ("Upcoming".equals(currentFilter)  &&  isCompleted) continue;
            if ("Completed".equals(currentFilter) && !isCompleted) continue;

            View item = inflater.inflate(R.layout.item_assignment, container, false);

            ((TextView) item.findViewById(R.id.assignmentTitle)).setText(assignment.getTitle());
            ((TextView) item.findViewById(R.id.assignmentSubject)).setText(assignment.getSubject());

            // Show submission status in due date field
            String statusText = assignment.getDueDate();
            if (assignment.isSubmitted()) {
                statusText += " • " + assignment.getSubmissionStatus();
            }
            ((TextView) item.findViewById(R.id.dueDate)).setText(statusText);

            TextView badge = item.findViewById(R.id.priorityBadge);
            String priority = assignment.getPriority();
            badge.setText(priority + " Priority");

            // Set background color based on priority
            if ("High".equals(priority)) {
                badge.setBackgroundResource(R.drawable.priority_high_bg);
            } else if ("Medium".equals(priority)) {
                badge.setBackgroundResource(R.drawable.priority_medium_bg);
            } else {
                badge.setBackgroundResource(R.drawable.priority_low_bg);
            }

            // Add click listener to open assignment detail
            final int position = i;
            item.setOnClickListener(v -> {
                Intent intent = new Intent(this, AssignmentDetailActivity.class);
                intent.putExtra("assignment", assignment);
                intent.putExtra("position", position);
                assignmentDetailLauncher.launch(intent);
            });

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
