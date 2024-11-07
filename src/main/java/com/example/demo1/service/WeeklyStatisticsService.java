package com.example.demo1.service;

import com.example.demo1.model.WeeklyStatistics;
import java.util.Optional;

public interface WeeklyStatisticsService {
    Optional<WeeklyStatistics> getCurrentWeekStatistics();
    void incrementUserCount();
    void incrementIncome(double amount);
    void incrementAppointments();
}
