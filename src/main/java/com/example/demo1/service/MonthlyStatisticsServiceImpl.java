package com.example.demo1.service;

import com.example.demo1.model.MonthlyStatistics;
import com.example.demo1.repository.MonthlyStatisticsRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonthlyStatisticsServiceImpl implements MonthlyStatisticsService {

    private final MonthlyStatisticsRepository monthlyStatisticsRepository;

    public MonthlyStatisticsServiceImpl(MonthlyStatisticsRepository monthlyStatisticsRepository) {
        this.monthlyStatisticsRepository = monthlyStatisticsRepository;
    }

    @Override
    public Optional<MonthlyStatistics> getCurrentMonthStatistics() {
        return monthlyStatisticsRepository.getCurrentMonthStatistics();
    }

    @Override
    public void incrementUserCount() {
        monthlyStatisticsRepository.incrementUserCount();
    }

    @Override
    public void incrementIncome(double amount) {
        monthlyStatisticsRepository.incrementIncome(amount);
    }

    @Override
    public void incrementAppointments() {
        monthlyStatisticsRepository.incrementAppointments();
    }
}
