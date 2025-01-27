package com.example.statisticsservice.repository;

import com.example.statisticsservice.model.BorrowStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BorrowStatisticsRepository extends MongoRepository<BorrowStatistics, String> {

    // Find the latest statistics by date
    Optional<BorrowStatistics> findFirstByOrderByDateDesc();
}