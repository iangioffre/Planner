package com.example.planner;

public class Assignment extends Event {

    // fields

    private int isDone;

    // constructors

    public Assignment() {
        super();
        isDone = 0;
    }

    public Assignment(int id, String title, String dateTime, String course, int priority, String notes, int isDone) {
        super(id, title, dateTime, course, priority, notes);
        this.isDone = isDone;
    }

    // methods

    public int getIsDone() {
        return isDone;
    }

    public void setIsDone(int isDone) {
        this.isDone = isDone;
    }
}