package com.example.demo1.service;

import com.example.demo1.model.MonthlyStatistics;
import java.util.Optional;

public interface MonthlyStatisticsService {
    Optional<MonthlyStatistics> getCurrentMonthStatistics();
    void incrementUserCount();
    void incrementIncome(double amount);
    void incrementAppointments();
}
