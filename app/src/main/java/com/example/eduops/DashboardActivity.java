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

    private static final Object[][] COURSE_DATA = {
            {"MOBILE DEVELOPMENT", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2203N - January 2026", 75},
            {"NC IV CERTIFICATE 2", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2206N - January 2026", 75},
            {"DATA STRUCTURES AND\nALGORITHMS", "AY 2025 - 2026, 2ND SEMESTER", "CIS 2201 - January 2026", 75},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ListView listView = findViewById(R.id.coursesListView);
        List<Course> courses = new ArrayList<>();
        for (Object[] d : COURSE_DATA) {
            courses.add(new Course((String) d[0], (String) d[1], (String) d[2], (int) d[3]));
        }

        listView.setAdapter(new CourseAdapter(this, courses));
        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.navAnnouncements).setOnClickListener(v -> startActivity(new Intent(this, AnnouncementsActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
        findViewById(R.id.navGrades).setOnClickListener(v -> startActivity(new Intent(this, GradesActivity.class)));
    }

    static class Course {
        String title, semester, code;
        int progress;
        Course(String t, String s, String c, int p) { title=t; semester=s; code=c; progress=p; }
    }

    static class CourseAdapter extends BaseAdapter {
        private final Context context;
        private final List<Course> list;
        CourseAdapter(Context c, List<Course> l) { context=c; list=l; }
        @Override public int getCount() { return list.size(); }
        @Override public Object getItem(int i) { return list.get(i); }
        @Override public long getItemId(int i) { return i; }
        @Override public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = LayoutInflater.from(context).inflate(R.layout.view_course_group, parent, false);
            Course c = list.get(i);
            ((TextView) view.findViewById(R.id.groupTitle)).setText(c.title);
            ((TextView) view.findViewById(R.id.groupSemester)).setText(c.semester);
            ((TextView) view.findViewById(R.id.courseCode)).setText(c.code);
            ((ProgressBar) view.findViewById(R.id.progressBar)).setProgress(c.progress);
            ((TextView) view.findViewById(R.id.progressPercent)).setText(c.progress + "%");
            ((TextView) view.findViewById(R.id.progressLabel)).setText("Progress: " + c.progress + "%");
            return view;
        }
    }
}
