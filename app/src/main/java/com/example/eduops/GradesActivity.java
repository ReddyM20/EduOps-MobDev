package com.example.eduops;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends AppCompatActivity {

    private static final String[][] GRADES_DATA = {
            {"CIS 2101",  "NO GRADE"},
            {"CIS 2103N", "PASS"},
            {"CIS 2106N", "FAIL"},
            {"CIS 4210N", "PASS"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        ListView listView = findViewById(R.id.gradesListView);
        List<Grade> grades = new ArrayList<>();
        for (String[] d : GRADES_DATA) {
            grades.add(new Grade(d[0], d[1]));
        }

        listView.setAdapter(new GradeAdapter(this, grades));
        setupNavigation();
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.navAnnouncements).setOnClickListener(v -> startActivity(new Intent(this, AnnouncementsActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
    }

    static class Grade {
        String course, status;
        Grade(String c, String s) { course=c; status=s; }
    }

    static class GradeAdapter extends BaseAdapter {
        private final Context context;
        private final List<Grade> list;
        GradeAdapter(Context c, List<Grade> l) { context=c; list=l; }
        @Override public int getCount() { return list.size(); }
        @Override public Object getItem(int i) { return list.get(i); }
        @Override public long getItemId(int i) { return i; }
        @Override public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = LayoutInflater.from(context).inflate(R.layout.item_grade_row, parent, false);
            Grade g = list.get(i);
            
            ((TextView) view.findViewById(R.id.gradeCourseName)).setText(g.course);
            TextView statusView = view.findViewById(R.id.gradeStatus);
            statusView.setText(g.status);
            
            switch (g.status) {
                case "PASS": statusView.setTextColor(Color.parseColor("#2E7D32")); break;
                case "FAIL": statusView.setTextColor(Color.parseColor("#C62828")); break;
                default: statusView.setTextColor(Color.parseColor("#757575")); break;
            }

            view.findViewById(R.id.detailsButton).setOnClickListener(v ->
                    Toast.makeText(context, "Details for " + g.course, Toast.LENGTH_SHORT).show());

            return view;
        }
    }
}
