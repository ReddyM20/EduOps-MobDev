package com.example.eduops;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class AssignmentDetailActivity extends AppCompatActivity {

    private Assignment assignment;
    private TextView selectedFileText;
    private Button submitButton;
    private Uri selectedFileUri;
    private String selectedFileName;

    private ActivityResultLauncher<Intent> filePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);

        // Get assignment from intent
        assignment = (Assignment) getIntent().getSerializableExtra("assignment");
        int assignmentPosition = getIntent().getIntExtra("position", -1);

        if (assignment == null) {
            Toast.makeText(this, "Error loading assignment", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupFilePickerLauncher();
        setupViews();
        updateUI();
    }

    private void setupFilePickerLauncher() {
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        selectedFileUri = result.getData().getData();
                        if (selectedFileUri != null) {
                            selectedFileName = getFileName(selectedFileUri);
                            selectedFileText.setText("Selected: " + selectedFileName);
                            selectedFileText.setVisibility(View.VISIBLE);
                            submitButton.setEnabled(true);
                        }
                    }
                }
        );
    }

    private void setupViews() {
        findViewById(R.id.backButton).setOnClickListener(v -> {
            // Pass the updated assignment back
            Intent resultIntent = new Intent();
            resultIntent.putExtra("assignment", assignment);
            resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        TextView assignmentTitle = findViewById(R.id.assignmentTitle);
        TextView assignmentSubject = findViewById(R.id.assignmentSubject);
        TextView assignmentDueDate = findViewById(R.id.assignmentDueDate);
        TextView assignmentDescription = findViewById(R.id.assignmentDescription);
        TextView priorityBadge = findViewById(R.id.priorityBadge);

        assignmentTitle.setText(assignment.getTitle());
        assignmentSubject.setText(assignment.getSubject());
        assignmentDueDate.setText(assignment.getDueDate());
        assignmentDescription.setText(assignment.getDescription());

        String priority = assignment.getPriority();
        priorityBadge.setText(priority + " Priority");
        if ("High".equals(priority)) {
            priorityBadge.setBackgroundResource(R.drawable.priority_high_bg);
        } else if ("Medium".equals(priority)) {
            priorityBadge.setBackgroundResource(R.drawable.priority_medium_bg);
        } else {
            priorityBadge.setBackgroundResource(R.drawable.priority_low_bg);
        }

        selectedFileText = findViewById(R.id.selectedFileText);
        Button selectFileButton = findViewById(R.id.selectFileButton);
        submitButton = findViewById(R.id.submitButton);

        selectFileButton.setOnClickListener(v -> openFilePicker());
        submitButton.setOnClickListener(v -> submitAssignment());
    }

    private void updateUI() {
        TextView submissionStatus = findViewById(R.id.submissionStatus);
        LinearLayout submissionDetailsSection = findViewById(R.id.submissionDetailsSection);
        LinearLayout gradeSection = findViewById(R.id.gradeSection);
        Button selectFileButton = findViewById(R.id.selectFileButton);

        submissionStatus.setText(assignment.getSubmissionStatus());

        if (assignment.isSubmitted()) {
            // Hide file selection controls if already submitted
            selectFileButton.setVisibility(View.GONE);
            submitButton.setVisibility(View.GONE);
            selectedFileText.setVisibility(View.GONE);

            // Show submission details
            submissionDetailsSection.setVisibility(View.VISIBLE);
            TextView submittedFileName = findViewById(R.id.submittedFileName);
            TextView submissionDate = findViewById(R.id.submissionDate);
            submittedFileName.setText(assignment.getSubmittedFileName());
            submissionDate.setText(assignment.getSubmissionDate());

            submissionStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark, null));

            // Show grade if graded
            if (assignment.getGrade() != null) {
                gradeSection.setVisibility(View.VISIBLE);
                TextView gradeText = findViewById(R.id.gradeText);
                TextView feedbackText = findViewById(R.id.feedbackText);
                gradeText.setText(assignment.getGrade() + "/100");
                feedbackText.setText(assignment.getTeacherFeedback() != null ? assignment.getTeacherFeedback() : "No feedback provided");
                submissionStatus.setText("Graded");
            }
        } else {
            submissionStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark, null));
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        filePickerLauncher.launch(Intent.createChooser(intent, "Select File"));
    }

    private String getFileName(Uri uri) {
        String fileName = "unknown_file";
        if (uri.getScheme().equals("content")) {
            android.database.Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME);
                if (nameIndex >= 0) {
                    fileName = cursor.getString(nameIndex);
                }
                cursor.close();
            }
        } else if (uri.getScheme().equals("file")) {
            fileName = uri.getLastPathSegment();
        }
        return fileName;
    }

    private void submitAssignment() {
        if (selectedFileUri == null) {
            Toast.makeText(this, "Please select a file first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Submit the assignment
        assignment.submitAssignment(selectedFileName);
        Toast.makeText(this, "Assignment submitted successfully!", Toast.LENGTH_LONG).show();

        // Update UI to reflect submission
        updateUI();
    }

    @Override
    public void onBackPressed() {
        // Pass the updated assignment back
        Intent resultIntent = new Intent();
        resultIntent.putExtra("assignment", assignment);
        resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
