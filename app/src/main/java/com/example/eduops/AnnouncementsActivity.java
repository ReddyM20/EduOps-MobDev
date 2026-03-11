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

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity {

    private static final String BODY_TEXT =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
            "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam...  ";

    private static final String[][] ANNOUNCEMENTS_DATA = {
            {"John Carlo", "CIS 2101 Coordinator", "2 hours ago", "Test Post"},
            {"John Carlo", "CIS 2203N Coordinator", "2 hours ago", "Test Post"},
            {"John Carlo", "CIS 2206N Coordinator", "2 hours ago", "Test Post"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        ListView listView = findViewById(R.id.announcementsListView);
        List<Announcement> list = new ArrayList<>();
        for (String[] d : ANNOUNCEMENTS_DATA) {
            list.add(new Announcement(d[0], d[1], d[2], d[3]));
        }

        listView.setAdapter(new AnnouncementAdapter(this, list));
        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
        findViewById(R.id.navGrades).setOnClickListener(v -> startActivity(new Intent(this, GradesActivity.class)));
    }

    static class Announcement {
        String name, role, time, title;
        Announcement(String n, String r, String tm, String t) { name=n; role=r; time=tm; title=t; }
    }

    static class AnnouncementAdapter extends BaseAdapter {
        private final Context context;
        private final List<Announcement> list;
        AnnouncementAdapter(Context c, List<Announcement> l) { context=c; list=l; }
        @Override public int getCount() { return list.size(); }
        @Override public Object getItem(int i) { return list.get(i); }
        @Override public long getItemId(int i) { return i; }
        @Override public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = LayoutInflater.from(context).inflate(R.layout.item_announcement, parent, false);
            Announcement a = list.get(i);
            ((TextView) view.findViewById(R.id.posterName)).setText(a.name);
            ((TextView) view.findViewById(R.id.posterRole)).setText(a.role);
            ((TextView) view.findViewById(R.id.postTime)).setText(a.time);
            ((TextView) view.findViewById(R.id.postTitle)).setText(a.title);

            SpannableStringBuilder sb = new SpannableStringBuilder(BODY_TEXT);
            int start = sb.length();
            sb.append("See More");
            sb.setSpan(new StyleSpan(Typeface.BOLD), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) view.findViewById(R.id.postBody)).setText(sb);
            return view;
        }
    }
}
