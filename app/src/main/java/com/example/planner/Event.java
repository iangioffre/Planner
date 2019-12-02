package com.example.planner;

public class Event {

    // fields

    private String title;
    private String dateTime;
    private String course;
    private int priority;
    private String notes;

    // constructors

    public Event() {
        title = "";
        dateTime = "";
        course = "";
        priority = 1;
        notes = "";
    }

    public Event(String title, String dateTime, String course, int priority, String notes) {
        this.title = title;
        this.dateTime = dateTime;
        this.course = course;
        this.priority = priority;
        this.notes = notes;
    }

    // methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
