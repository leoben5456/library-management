package com.example.statisticsservice.service;

import com.example.statisticsservice.model.UsersStatistics;
import com.example.statisticsservice.repository.UserStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersStatisticsService {

    private final UsersStatisticsFetcherService fetcherService;
    private final UserStatisticsRepository repository;

    public UsersStatisticsService(UsersStatisticsFetcherService fetcherService, UserStatisticsRepository repository) {
        this.fetcherService = fetcherService;
        this.repository = repository;
    }

    public void updateUserStatistics() {
        UsersStatistics newStats = fetcherService.fetchUserStatistics();

        // Get the most recent statistics for comparison
        Optional<UsersStatistics> latestStatsOptional = repository.findFirstByOrderByLastUpdatedDesc();
        if (latestStatsOptional.isPresent()) {
            UsersStatistics latestStats = latestStatsOptional.get();
            int percentageChange = calculateChangePercentage(latestStats.getTotalUsers(), newStats.getTotalUsers());
            newStats.setPercentageChange(percentageChange);
        } else {
            newStats.setPercentageChange(0); // No previous data available
        }

        repository.save(newStats);
    }

    private int calculateChangePercentage(int previous, int current) {
        if (previous == 0) {
            return 0;
        }
        return ((current - previous) * 100) / previous;
    }
}