package com.example.demo1.repository;

import com.example.demo1.model.WeeklyStatistics;
import java.util.Optional;

public interface WeeklyStatisticsRepository {

    Optional<WeeklyStatistics> getCurrentWeekStatistics();
    void incrementUserCount();
    void incrementIncome(double amount);
    void incrementAppointments();
    void resetWeeklyStatistics();
}
