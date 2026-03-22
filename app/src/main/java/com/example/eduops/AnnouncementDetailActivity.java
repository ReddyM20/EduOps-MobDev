package com.example.eduops;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AnnouncementDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail);

        // Get announcement from intent
        AnnouncementsActivity.Announcement announcement =
                (AnnouncementsActivity.Announcement) getIntent().getSerializableExtra("announcement");

        if (announcement == null) {
            finish();
            return;
        }

        // Setup views
        ImageView backButton = findViewById(R.id.backButton);
        TextView posterName = findViewById(R.id.posterName);
        TextView posterRole = findViewById(R.id.posterRole);
        TextView postTime = findViewById(R.id.postTime);
        TextView postTitle = findViewById(R.id.postTitle);
        TextView postContent = findViewById(R.id.postContent);

        // Set data
        posterName.setText(announcement.name);
        posterRole.setText(announcement.role);
        postTime.setText(announcement.time);
        postTitle.setText(announcement.title);
        postContent.setText(announcement.content);

        // Back button
        backButton.setOnClickListener(v -> finish());

        setupNavigation();
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

        findViewById(R.id.navAssignments).setOnClickListener(v ->
                startActivity(new Intent(this, AssignmentsActivity.class)));

        findViewById(R.id.navGrades).setOnClickListener(v ->
                startActivity(new Intent(this, GradesActivity.class)));
    }
}
