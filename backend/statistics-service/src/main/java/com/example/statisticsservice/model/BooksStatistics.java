package com.example.statisticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books_statistics")
public class BooksStatistics {
    @Id
    private String id; // e.g., "daily_2025-01-01"
    private LocalDate date;
    private int borrowedBooks;
    private int percentageChange;
    private int returnedBooks;
    private int overdueBooks;
    private int overdueBooksPercentageChange;
    private LocalDate lastUpdated;


}