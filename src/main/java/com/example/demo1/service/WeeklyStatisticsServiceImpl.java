package com.example.demo1.service;

import com.example.demo1.model.WeeklyStatistics;
import com.example.demo1.repository.WeeklyStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeeklyStatisticsServiceImpl implements WeeklyStatisticsService {

    private final WeeklyStatisticsRepository weeklyStatisticsRepository;

    public WeeklyStatisticsServiceImpl(WeeklyStatisticsRepository weeklyStatisticsRepository) {
        this.weeklyStatisticsRepository = weeklyStatisticsRepository;
    }

    @Override
    public Optional<WeeklyStatistics> getCurrentWeekStatistics() {
        return weeklyStatisticsRepository.getCurrentWeekStatistics();
    }

    @Override
    public void incrementUserCount() {
        weeklyStatisticsRepository.incrementUserCount();
    }

    @Override
    public void incrementIncome(double amount) {
        weeklyStatisticsRepository.incrementIncome(amount);
    }

    @Override
    public void incrementAppointments() {
        weeklyStatisticsRepository.incrementAppointments();
    }
}
