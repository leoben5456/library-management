package com.example.statisticsservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "statistics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    @Id
    private String id;
    private String month;

    private long userCount;
    private double userGrowthRate;

    private long booksBorrowed;
    private double booksBorrowedChangeRate;

    private long penaltiesThisMonth;
    private double penaltiesChangeRate;

    private long overdueBooks;
    private double overdueBooksChangeRate;

    private Map<String, Double> userBreakdown;

    private Map<String, Integer> borrowAndReturnStats;

    private List<Integer> monthlyPenalties;

    private LocalDateTime timestamp;
}

/*

{
  "_id": "64b0f6a2f5e3c7b8d1a7e012", // Document ID
  "userCount": 100, // Total number of users
  "userGrowthRate": 10.0, // User growth percentage
  "booksBorrowed": 80, // Total books borrowed
  "booksBorrowedChangeRate": -5.0, // Change in books borrowed percentage
  "penaltiesThisMonth": 150, // Penalties for the current month
  "penaltiesChangeRate": -15.0, // Change in penalties percentage
  "overdueBooks": 200, // Overdue books
  "overdueBooksChangeRate": 20.0, // Change in overdue books percentage
  "userBreakdown": { // Breakdown of users
    "Students": 44.4, // 44.4% are students
    "Professors": 55.6 // 55.6% are professors
  },
  "borrowAndReturnStats": { // Daily statistics
    "Monday": 40,
    "Tuesday": 30,
    "Wednesday": 20,
    "Thursday": 120,
    "Friday": 80,
    "Saturday": 33,
    "Sunday": 100
  },
  "monthlyPenalties": [ // Penalties by month
    10, 20, 30, 40, 50, 60, 70, 80, 150, 0, 0, 0 // Jan to Dec
  ],
  "timestamp": "2025-01-01T10:00:00" // Timestamp of this data snapshot
}

 */