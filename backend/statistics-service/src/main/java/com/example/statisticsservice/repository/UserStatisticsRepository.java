package com.example.statisticsservice.repository;

import com.example.statisticsservice.model.UsersStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserStatisticsRepository extends MongoRepository<UsersStatistics, String> {
}
