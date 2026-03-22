package com.example.eduops;

import java.io.Serializable;

public class Lesson implements Serializable {
    public enum ContentType {
        TEXT,
        PDF,
        VIDEO
    }

    private String lessonId;
    private String title;
    private String description;
    private ContentType contentType;
    private String contentUrl;
    private String textContent;
    private int durationMinutes;

    public Lesson(String lessonId, String title, String description, ContentType contentType) {
        this.lessonId = lessonId;
        this.title = title;
        this.description = description;
        this.contentType = contentType;
    }

    // For TEXT type lessons
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    // For PDF and VIDEO type lessons
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public String getTextContent() {
        return textContent;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getContentTypeString() {
        switch (contentType) {
            case TEXT:
                return "Reading";
            case PDF:
                return "PDF Document";
            case VIDEO:
                return "Video";
            default:
                return "Content";
        }
    }
}
