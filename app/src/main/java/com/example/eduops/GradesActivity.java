package com.example.eduops;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class GradesActivity extends AppCompatActivity {

    private List<Grade> gradesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        initializeGrades();

        ListView listView = findViewById(R.id.gradesListView);
        listView.setAdapter(new GradeAdapter(this, gradesList));
        setupNavigation();
    }

    private void initializeGrades() {
        gradesList = MockDataFactory.getGrades();
    }

    public void showGradeDetailsDialog(Grade grade) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_grade_details);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Set course info
        TextView courseTitle = dialog.findViewById(R.id.dialogCourseTitle);
        TextView courseName = dialog.findViewById(R.id.dialogCourseName);
        TextView gpaValue = dialog.findViewById(R.id.dialogGpaValue);
        TextView letterGrade = dialog.findViewById(R.id.dialogLetterGrade);

        courseTitle.setText(grade.getCourseId());
        courseName.setText(grade.getCourseName());
        gpaValue.setText(grade.getGpaString());
        gpaValue.setTextColor(grade.getGpaColor());
        letterGrade.setText("(" + grade.getLetterGrade() + ")");

        LinearLayout scoresContainer = dialog.findViewById(R.id.scoresContainer);
        scoresContainer.removeAllViews();

        for (Grade.ScoreItem score : grade.getScores()) {
            View scoreView = LayoutInflater.from(this).inflate(R.layout.item_score_detail, scoresContainer, false);

            TextView category = scoreView.findViewById(R.id.scoreCategory);
            TextView itemName = scoreView.findViewById(R.id.scoreItemName);
            TextView scoreValue = scoreView.findViewById(R.id.scoreValue);
            ProgressBar progressBar = scoreView.findViewById(R.id.scoreProgressBar);
            TextView percentage = scoreView.findViewById(R.id.scorePercentage);

            category.setText(score.getCategory());
            itemName.setText(score.getItemName());
            scoreValue.setText(score.getScoreString());
            progressBar.setProgress((int) score.getPercentage());
            percentage.setText(score.getPercentageString());

            category.setBackgroundResource(R.drawable.button_blue_bg);

            // Set progress bar color based on score
            if (score.getPercentage() >= 80) {
                progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.success, null)));
            } else if (score.getPercentage() >= 60) {
                progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.warning, null)));
            } else {
                progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(getResources().getColor(R.color.error, null)));
            }

            scoresContainer.addView(scoreView);
        }

        dialog.findViewById(R.id.closeButton).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void setupNavigation() {
        findViewById(R.id.navDashboard).setOnClickListener(v -> startActivity(new Intent(this, DashboardActivity.class)));
        findViewById(R.id.navAnnouncements).setOnClickListener(v -> startActivity(new Intent(this, AnnouncementsActivity.class)));
        findViewById(R.id.navAssignments).setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));
    }

    static class GradeAdapter extends BaseAdapter {
        private final GradesActivity activity;
        private final List<Grade> list;

        GradeAdapter(GradesActivity activity, List<Grade> list) {
            this.activity = activity;
            this.list = list;
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
                view = LayoutInflater.from(activity).inflate(R.layout.item_grade_row, parent, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            Grade g = list.get(i);
            holder.courseName.setText(g.getCourseId());
            holder.gpaView.setText(g.getGpaString());
            holder.gpaView.setTextColor(g.getGpaColor());
            holder.detailsButton.setOnClickListener(v -> activity.showGradeDetailsDialog(g));

            return view;
        }

        static class ViewHolder {
            TextView courseName, gpaView;
            ImageView detailsButton;

            ViewHolder(View view) {
                courseName = view.findViewById(R.id.gradeCourseName);
                gpaView = view.findViewById(R.id.gradeStatus);
                detailsButton = view.findViewById(R.id.detailsButton);
            }
        }
    }
}
