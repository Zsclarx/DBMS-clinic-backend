package com.example.demo1.controller;

import com.example.demo1.model.MonthlyStatistics;
import com.example.demo1.service.MonthlyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/monthly-statistics")
public class MonthlyStatisticsController {

    private final MonthlyStatisticsService monthlyStatisticsService;

    @Autowired
    public MonthlyStatisticsController(MonthlyStatisticsService monthlyStatisticsService) {
        this.monthlyStatisticsService = monthlyStatisticsService;
    }

    @GetMapping("/current")
    public Optional<MonthlyStatistics> getCurrentMonthStatistics() {
        return monthlyStatisticsService.getCurrentMonthStatistics();
    }

    @PostMapping("/increment/user")
    public void incrementUserCount() {
        monthlyStatisticsService.incrementUserCount();
    }

    @PostMapping("/increment/income")
    public void incrementIncome(@RequestParam double amount) {
        monthlyStatisticsService.incrementIncome(amount);
    }

    @PostMapping("/increment/appointment")
    public void incrementAppointments() {
        monthlyStatisticsService.incrementAppointments();
    }
}
