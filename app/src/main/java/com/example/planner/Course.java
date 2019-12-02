package com.example.planner;

public class Course {

    // fields

    private int id;
    private String name;
    private int onMonday;
    private int onTuesday;
    private int onWednesday;
    private int onThursday;
    private int onFriday;
    private String startTime;
    private String endTime;

    // constructors

    public Course() {
        id = -1;
        name = "";
        onMonday = 0;
        onTuesday = 0;
        onWednesday = 0;
        onThursday = 0;
        onFriday = 0;
        startTime = "";
        endTime = "";
    }

    public Course(int id, String name, int onMonday, int onTuesday, int onWednesday, int onThursday, int onFriday, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.onMonday = onMonday;
        this.onTuesday = onTuesday;
        this.onWednesday = onWednesday;
        this.onThursday = onThursday;
        this.onFriday = onFriday;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // methods

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOnMonday() {
        return onMonday;
    }

    public void setOnMonday(int onMonday) {
        this.onMonday = onMonday;
    }

    public int getOnTuesday() {
        return onTuesday;
    }

    public void setOnTuesday(int onTuesday) {
        this.onTuesday = onTuesday;
    }

    public int getOnWednesday() {
        return onWednesday;
    }

    public void setOnWednesday(int onWednesday) {
        this.onWednesday = onWednesday;
    }

    public int getOnThursday() {
        return onThursday;
    }

    public void setOnThursday(int onThursday) {
        this.onThursday = onThursday;
    }

    public int getOnFriday() {
        return onFriday;
    }

    public void setOnFriday(int onFriday) {
        this.onFriday = onFriday;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
