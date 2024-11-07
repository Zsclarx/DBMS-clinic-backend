package com.example.demo1.controller;

import com.example.demo1.model.WeeklyStatistics;
import com.example.demo1.service.WeeklyStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/weekly-statistics")
public class WeeklyStatisticsController {

    private final WeeklyStatisticsService weeklyStatisticsService;

    @Autowired
    public WeeklyStatisticsController(WeeklyStatisticsService weeklyStatisticsService) {
        this.weeklyStatisticsService = weeklyStatisticsService;
    }

    @GetMapping("/current")
    public Optional<WeeklyStatistics> getCurrentWeekStatistics() {
        return weeklyStatisticsService.getCurrentWeekStatistics();
    }

    @PostMapping("/increment/user")
    public void incrementUserCount() {
        weeklyStatisticsService.incrementUserCount();
    }

    @PostMapping("/increment/income")
    public void incrementIncome(@RequestParam double amount) {
        weeklyStatisticsService.incrementIncome(amount);
    }

    @PostMapping("/increment/appointment")
    public void incrementAppointments() {
        weeklyStatisticsService.incrementAppointments();
    }
}
