package com.example.demo1.model;

public class MonthlyStatistics {
    private int statID;
    private String monthName;
    private int totalAppointments;
    private double totalIncome;
    private int totalUsersRegistered;

    public MonthlyStatistics() {
    }

    // Getters and Setters
    // ...

    public int getStatID() {
        return statID;
    }

    public void setStatID(int statID) {
        this.statID = statID;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
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
