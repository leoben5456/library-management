package com.example.statisticsservice.service;


import com.example.statisticsservice.model.UsersStatistics;
import com.example.statisticsservice.repository.StatisticsRepository;
import com.example.statisticsservice.repository.UserStatisticsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@Service
//public class consumeStatistics {
//
//    private final UserStatisticsRepository userStatisticsRepository;
//
//    public consumeStatistics(UserStatisticsRepository userStatisticsRepository) {
//
//        this.userStatisticsRepository = userStatisticsRepository;
//    }
//
//
//    @KafkaListener(topics = "user-count-topic", groupId = "statistics-service-group")
//    public void consumeUserCount(Long userCount) {
//
//        System.out.println("Total number of users: " + userCount);
//        UsersStatistics stats = userStatisticsRepository.findById("current").orElse(null);
//
//
//        if(stats!=null) {
//            stats.setTotalUsers(userCount.intValue());
//            stats.setLastUpdated(LocalDateTime.now());
//            userStatisticsRepository.save(stats);
//        } else {
//            stats = new UsersStatistics("current", userCount.intValue(), null, 0, LocalDateTime.now());
//            userStatisticsRepository.save(stats);
//        }
//
//        System.out.println("Stats: " + stats);
//    }
//}
