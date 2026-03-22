package com.example.eduops;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity {

    private List<Announcement> announcementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        initializeAnnouncements();

        ListView listView = findViewById(R.id.announcementsListView);
        listView.setAdapter(new AnnouncementAdapter(this, announcementsList));

        // Add click listener to open announcement detail
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Announcement announcement = announcementsList.get(position);
            Intent intent = new Intent(this, AnnouncementDetailActivity.class);
            intent.putExtra("announcement", announcement);
            startActivity(intent);
        });

        setupNavigation();
    }

    private void initializeAnnouncements() {
        announcementsList = new ArrayList<>();

        // Announcement 1
        announcementsList.add(new Announcement(
                "Jane Carla",
                "CIS 2201 Coordinator",
                "2 hours ago",
                "Midterm Exam Schedule",
                "Good day, students! This is to announce that our midterm examination for Data Structures and Algorithms " +
                        "will be held on March 28, 2026, from 8:00 AM to 10:00 AM in Room 301. The exam will cover all topics " +
                        "from Week 1 to Week 7, including Arrays, Linked Lists, Stacks, Queues, Trees, and Sorting Algorithms. " +
                        "Please bring your student ID, pen, and calculator. Late arrivals will not be accommodated. " +
                        "Study well and good luck to everyone!"
        ));

        // Announcement 2
        announcementsList.add(new Announcement(
                "Paule Glenn Acuin",
                "CIS 2203N Coordinator",
                "1 hours ago",
                "Assignment Deadline Extended",
                "Hi everyone! Due to the technical difficulties some students experienced with the submission portal, " +
                        "I have decided to extend the deadline for the Mobile Development assignment. The new deadline is " +
                        "April 6, 2026, 11:59 PM. This applies to the \"Do It Yourself\" project where you need to create " +
                        "a mobile app with CRUD functionality. Make sure to include proper documentation and a demo video. " +
                        "If you have any questions, feel free to message me or post in the discussion board."
        ));

        // Announcement 3
        announcementsList.add(new Announcement(
                "John Carlo",
                "CIS 2206N Coordinator",
                "2 hours ago",
                "Important: Class Schedule Change",
                "Attention NC IV Certificate students! Please be informed that our class schedule for next week " +
                        "has been changed. Our Monday session (March 25) will be moved to Tuesday (March 26) at the same time " +
                        "(2:00 PM - 5:00 PM). This is due to a departmental meeting that I need to attend. We will use the " +
                        "same room (Computer Lab 2). Please mark your calendars and inform your classmates who might miss " +
                        "this announcement. Thank you for your understanding and see you all next week!"
        ));
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
        findViewById(R.id.navGrades).setOnClickListener(v -> startActivity(new Intent(this, GradesActivity.class)));
    }

    static class Announcement implements Serializable {
        String name, role, time, title, content;

        Announcement(String name, String role, String time, String title, String content) {
            this.name = name;
            this.role = role;
            this.time = time;
            this.title = title;
            this.content = content;
        }

        String getPreview() {
            if (content.length() > 120) {
                return content.substring(0, 120) + "...  ";
            }
            return content + "  ";
        }
    }

    static class AnnouncementAdapter extends BaseAdapter {
        private final Context context;
        private final List<Announcement> list;

        AnnouncementAdapter(Context c, List<Announcement> l) {
            context = c;
            list = l;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_announcement, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Announcement a = list.get(i);
            holder.posterName.setText(a.name);
            holder.posterRole.setText(a.role);
            holder.postTime.setText(a.time);
            holder.postTitle.setText(a.title);

            // Show preview with "See More" in bold
            SpannableStringBuilder sb = new SpannableStringBuilder(a.getPreview());
            int start = sb.length();
            sb.append("See More");
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.postBody.setText(sb);
            return view;
        }

        static class ViewHolder {
            TextView posterName, posterRole, postTime, postTitle, postBody;

            ViewHolder(View view) {
                posterName = view.findViewById(R.id.posterName);
                posterRole = view.findViewById(R.id.posterRole);
                postTime = view.findViewById(R.id.postTime);
                postTitle = view.findViewById(R.id.postTitle);
                postBody = view.findViewById(R.id.postBody);
            }
        }
    }
}
