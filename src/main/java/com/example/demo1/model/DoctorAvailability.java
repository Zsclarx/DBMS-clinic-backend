package com.example.demo1.model;

import java.sql.Time;
import java.sql.Date;

public class DoctorAvailability {
    private int availabilityId;
    private int doctorId;
    private Time timeOfService;
    private Date date;
    private String status;

    public DoctorAvailability() {
    }

    public int getAvailabilityId() {
        return availabilityId;
    }

    public void setAvailabilityId(int availabilityId) {
        this.availabilityId = availabilityId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Time getTimeOfService() {
        return timeOfService;
    }

    public void setTimeOfService(Time timeOfService) {
        this.timeOfService = timeOfService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
