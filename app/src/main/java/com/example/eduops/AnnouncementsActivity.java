package com.example.eduops;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AnnouncementsActivity extends AppCompatActivity {

    private static final String BODY_TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam...  ";

    // { posterName, role, timeAgo, postTitle }
    private static final String[][] ANNOUNCEMENTS = {
            {"John Carlo", "CIS 2101 Coordinator", "2 hours ago", "Test Post"},
            {"John Carlo", "CIS 2203N Coordinator", "2 hours ago", "Test Post"},
            {"John Carlo", "CIS 2206N Coordinator", "2 hours ago", "Test Post"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        populateAnnouncements();
        setupNavigation();
    }

    private void populateAnnouncements() {
        LinearLayout container = findViewById(R.id.announcementsContainer);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String[] data : ANNOUNCEMENTS) {
            View item = inflater.inflate(R.layout.item_announcement, container, false);

            ((TextView) item.findViewById(R.id.posterName)).setText(data[0]);
            ((TextView) item.findViewById(R.id.posterRole)).setText(data[1]);
            ((TextView) item.findViewById(R.id.postTime)).setText(data[2]);
            ((TextView) item.findViewById(R.id.postTitle)).setText(data[3]);

            // Build body text with bold "See More" appended
            SpannableStringBuilder sb = new SpannableStringBuilder(BODY_TEXT);
            int start = sb.length();
            sb.append("See More");
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) item.findViewById(R.id.postBody)).setText(sb);

            container.addView(item);
        }
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> {
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        findViewById(R.id.navAnnouncements).setOnClickListener(v -> {
            // already here
        });

        findViewById(R.id.navAssignments).setOnClickListener(v ->
                Toast.makeText(this, "Assignments coming soon", Toast.LENGTH_SHORT).show());

        findViewById(R.id.navGrades).setOnClickListener(v ->
                Toast.makeText(this, "Grades coming soon", Toast.LENGTH_SHORT).show());
    }
}
