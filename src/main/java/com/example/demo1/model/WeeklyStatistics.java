package com.example.demo1.model;

public class WeeklyStatistics {
    private int statID;
    private String week;
    private int totalAppointments;
    private double totalIncome;
    private int totalUsersRegistered;

    public WeeklyStatistics() {
    }

    // Getters and Setters
    // ...


    public int getStatID() {
        return statID;
    }

    public void setStatID(int statID) {
        this.statID = statID;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getTotalAppointments() {
        return totalAppointments;
    }

    public void setTotalAppointments(int totalAppointments) {
        this.totalAppointments = totalAppointments;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public int getTotalUsersRegistered() {
        return totalUsersRegistered;
    }

    public void setTotalUsersRegistered(int totalUsersRegistered) {
        this.totalUsersRegistered = totalUsersRegistered;
    }
}
