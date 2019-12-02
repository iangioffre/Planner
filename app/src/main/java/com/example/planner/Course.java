package com.example.planner;

public class Course {

    // fields

    private String name;
    private Boolean onMonday;
    private Boolean onTuesday;
    private Boolean onWednesday;
    private Boolean onThursday;
    private Boolean onFriday;
    private String startTime;
    private String endTime;

    // constructors

    public Course() {
        name = "";
        onMonday = false;
        onTuesday = false;
        onWednesday = false;
        onThursday = false;
        onFriday = false;
        startTime = "";
        endTime = "";
    }

    public Course(String name, Boolean onMonday, Boolean onTuesday, Boolean onWednesday, Boolean onThursday, Boolean onFriday, String startTime, String endTime) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnMonday() {
        return onMonday;
    }

    public void setOnMonday(Boolean onMonday) {
        this.onMonday = onMonday;
    }

    public Boolean getOnTuesday() {
        return onTuesday;
    }

    public void setOnTuesday(Boolean onTuesday) {
        this.onTuesday = onTuesday;
    }

    public Boolean getOnWednesday() {
        return onWednesday;
    }

    public void setOnWednesday(Boolean onWednesday) {
        this.onWednesday = onWednesday;
    }

    public Boolean getOnThursday() {
        return onThursday;
    }

    public void setOnThursday(Boolean onThursday) {
        this.onThursday = onThursday;
    }

    public Boolean getOnFriday() {
        return onFriday;
    }

    public void setOnFriday(Boolean onFriday) {
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
