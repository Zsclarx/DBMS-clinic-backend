package com.example.demo1.model;

import java.sql.Time;

public class TimeSlot {
    private int doctorID;
    private java.sql.Time timeslot;
    private String workday;

    public TimeSlot() {
    }

    // Getters and Setters
    // ...

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public Time getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Time timeslot) {
        this.timeslot = timeslot;
    }

    public String getWorkday() {
        return workday;
    }

    public void setWorkday(String workday) {
        this.workday = workday;
    }
}
