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

    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeCourses();

        ListView listView = findViewById(R.id.coursesListView);
        if (listView != null) {
            listView.setAdapter(new CourseAdapter(this, courses));
            listView.setOnItemClickListener((parent, view, position, id) -> {
                Course selectedCourse = courses.get(position);
                Intent intent = new Intent(this, CourseDetailActivity.class);
                intent.putExtra("course", selectedCourse);
                startActivity(intent);
            });
        }

        setupNavigation();
    }

    private void initializeCourses() {
        courses = MockDataFactory.getCourses();
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> { /* already here */ });
        findViewById(R.id.navAnnouncements).setOnClickListener(v -> startActivity(new Intent(this, AnnouncementsActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
        findViewById(R.id.navGrades).setOnClickListener(v -> startActivity(new Intent(this, GradesActivity.class)));
    }

    static class CourseAdapter extends BaseAdapter {
        private final Context context;
        private final List<Course> list;
        private final int[] progressValues = {90, 65, 40}; // Hardcoded progress for display

        CourseAdapter(Context c, List<Course> l) { context=c; list=l; }
        @Override public int getCount() { return list.size(); }
        @Override public Object getItem(int i) { return list.get(i); }
        @Override public long getItemId(int i) { return i; }
        @Override public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = LayoutInflater.from(context).inflate(R.layout.view_course_group, parent, false);
            Course c = list.get(i);
            int progress = (i < progressValues.length) ? progressValues[i] : 0;

            ((TextView) view.findViewById(R.id.groupTitle)).setText(c.getCourseName());
            ((TextView) view.findViewById(R.id.groupSemester)).setText("AY 2025 - 2026, 2ND SEMESTER");
            ((TextView) view.findViewById(R.id.courseCode)).setText(c.getCourseId() + " - January 2026");
            ((ProgressBar) view.findViewById(R.id.progressBar)).setProgress(progress);
            ((TextView) view.findViewById(R.id.progressPercent)).setText(progress + "%");
            ((TextView) view.findViewById(R.id.progressLabel)).setText("Progress: " + progress + "%");
            return view;
        }
    }
}
