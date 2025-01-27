package com.example.statisticsservice.Component;

import com.example.statisticsservice.service.BorrowStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BorrowStatisticsScheduler {

    private final BorrowStatisticsService statisticsService;

    public BorrowStatisticsScheduler(BorrowStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Scheduled(cron = "0 0 0 * * SUN") // Runs Weekly at midnight
    public void collectBorrowStatistics() {
        statisticsService.updateBorrowStatistics();
        System.out.println("Borrow statistics updated successfully!");
    }
}