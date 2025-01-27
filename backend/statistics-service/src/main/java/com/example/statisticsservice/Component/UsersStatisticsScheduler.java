package com.example.statisticsservice.Component;

import com.example.statisticsservice.service.UsersStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UsersStatisticsScheduler {

    private final UsersStatisticsService statisticsService;

    public UsersStatisticsScheduler(UsersStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Scheduled(cron = "0 0 0 * * SUN")  // Runs every Sunday at midnight
    public void collectUserStatistics() {
        statisticsService.updateUserStatistics();
        System.out.println("Weekly user statistics updated successfully!");
    }
}