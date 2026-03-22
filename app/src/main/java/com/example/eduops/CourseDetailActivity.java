package com.example.eduops;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetailActivity extends BaseActivity {

    private Course course;
    private LinearLayout lessonsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        course = (Course) getIntent().getSerializableExtra("course");

        if (course == null) {
            Toast.makeText(this, "Error loading course", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupViews();
        setupCommonNavigation();
        loadLessons();
    }

    private void setupViews() {
        TextView courseName = findViewById(R.id.courseName);
        TextView courseInstructor = findViewById(R.id.courseInstructor);
        TextView courseDescription = findViewById(R.id.courseDescription);
        ImageView backButton = findViewById(R.id.backButton);
        lessonsContainer = findViewById(R.id.lessonsContainer);

        courseName.setText(course.getCourseName());
        courseInstructor.setText("Instructor: " + course.getInstructor());
        courseDescription.setText(course.getDescription());

        backButton.setOnClickListener(v -> finish());
    }

    private void loadLessons() {
        lessonsContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Lesson lesson : course.getLessons()) {
            View lessonItem = inflater.inflate(R.layout.item_lesson, lessonsContainer, false);

            TextView lessonTitle = lessonItem.findViewById(R.id.lessonTitle);
            TextView lessonDescription = lessonItem.findViewById(R.id.lessonDescription);
            TextView lessonDuration = lessonItem.findViewById(R.id.lessonDuration);
            ImageView contentTypeIcon = lessonItem.findViewById(R.id.contentTypeIcon);

            lessonTitle.setText(lesson.getTitle());
            lessonDescription.setText(lesson.getDescription());
            lessonDuration.setText(lesson.getContentTypeString() + " • " + lesson.getDurationMinutes() + " mins");

            switch (lesson.getContentType()) {
                case TEXT:
                    contentTypeIcon.setImageResource(android.R.drawable.ic_menu_info_details);
                    break;
                case PDF:
                    contentTypeIcon.setImageResource(android.R.drawable.ic_menu_agenda);
                    break;
                case VIDEO:
                    contentTypeIcon.setImageResource(android.R.drawable.ic_media_play);
                    break;
            }

            lessonItem.setOnClickListener(v -> openLesson(lesson));
            lessonsContainer.addView(lessonItem);
        }
    }

    private void openLesson(Lesson lesson) {
        switch (lesson.getContentType()) {
            case TEXT:
                Toast.makeText(this, "Text: " + lesson.getTextContent(), Toast.LENGTH_LONG).show();
                break;
            case PDF:
            case VIDEO:
                if (lesson.getContentUrl() != null && !lesson.getContentUrl().isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lesson.getContentUrl()));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(this, "Content URL not available", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
