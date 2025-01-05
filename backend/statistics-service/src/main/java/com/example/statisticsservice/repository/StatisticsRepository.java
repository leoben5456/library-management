package com.example.statisticsservice.repository;

import com.example.statisticsservice.model.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
}
