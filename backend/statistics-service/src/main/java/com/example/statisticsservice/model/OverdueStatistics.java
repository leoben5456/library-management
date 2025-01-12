package com.example.statisticsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "overdue_statistics")
public class OverdueStatistics {

    @Id
    private String id;
    private LocalDate date;          // snapshot date
    private int overdueBooks;        // e.g., 200
    private double overdueChangeRate; // e.g., +20%
}