package com.example.statisticsservice.service;

import com.example.statisticsservice.model.BorrowStatistics;
import com.example.statisticsservice.repository.BorrowStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BorrowStatisticsService {

    private final BorrowStatisticsFetcherService fetcherService;
    private final BorrowStatisticsRepository repository;

    public BorrowStatisticsService(BorrowStatisticsFetcherService fetcherService, BorrowStatisticsRepository repository) {
        this.fetcherService = fetcherService;
        this.repository = repository;
    }

    public void updateBorrowStatistics() {
        BorrowStatistics newStats = fetcherService.fetchStatisticsFromReservationService();

        // Get the most recent statistics for comparison
        Optional<BorrowStatistics> latestStatsOptional = repository.findFirstByOrderByDateDesc();
        if (latestStatsOptional.isPresent()) {
            BorrowStatistics latestStats = latestStatsOptional.get();
            double changeRate = calculateChangeRate(latestStats.getTotalBorrowed(), newStats.getTotalBorrowed());
            newStats.setBorrowedChangeRate(changeRate);
        } else {
            newStats.setBorrowedChangeRate(0.0); // No previous data available
        }

        repository.save(newStats);
    }

    private double calculateChangeRate(int previous, int current) {
        if (previous == 0) {
            return 0.0;
        }
        return ((current - previous) / (double) previous) * 100;
    }
}