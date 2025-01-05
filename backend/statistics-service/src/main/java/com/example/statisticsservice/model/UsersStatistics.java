package com.example.statisticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users_statistics")
public class UsersStatistics {
    @Id
    private String id; // Use "current" as static ID for the latest stats
    private int totalUsers; // Total number of users (user service)
    private Map<String, Double> userBreakdown; // (user service)
    private int percentageChange;
    private LocalDateTime lastUpdated;
}
