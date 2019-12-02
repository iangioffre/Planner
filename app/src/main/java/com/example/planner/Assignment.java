package com.example.planner;

public class Assignment extends Event {

    // fields

    private Boolean isDone;

    // constructors

    public Assignment() {
        super();
        isDone = false;
    }

    public Assignment(String title, String dateTime, String course, int priority, String notes, Boolean isDone) {
        super(title, dateTime, course, priority, notes);
        this.isDone = isDone;
    }


    // methods

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}
