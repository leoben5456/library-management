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
    private String id;
    private int totalUsers;
    private Map<String, Integer> userBreakdown;
    private int percentageChange;
    private LocalDateTime lastUpdated;
}
