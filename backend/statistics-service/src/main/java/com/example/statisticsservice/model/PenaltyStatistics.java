package com.example.statisticsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "penalty_statistics")
public class PenaltyStatistics {

    @Id
    private String id;
    private LocalDate date;         // day or month snapshot
    private int totalPenalties;     // e.g., 150 for "Penalties This Month"
    private double penaltyChangeRate; // e.g., -15% from last period

    // For the line chart across months (Jan–Sep):
    // Could store each month & value in a sub-list
    private List<MonthlyPenalty> penaltiesOverTime;
}