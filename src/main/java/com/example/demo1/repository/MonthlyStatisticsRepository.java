package com.example.demo1.repository;

import com.example.demo1.model.MonthlyStatistics;
import java.util.Optional;

public interface MonthlyStatisticsRepository {
    Optional<MonthlyStatistics> getCurrentMonthStatistics();
    void incrementUserCount();
    void incrementIncome(double amount);
    void incrementAppointments();
    void resetMonthlyStatistics();
}
