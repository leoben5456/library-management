package com.example.statisticsservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "borrow_statistics")
public class BorrowStatistics {

    @Id
    private String id;
    private LocalDate date;               // e.g., day for snapshot
    private int totalBorrowed;            // e.g., 80
    private double borrowedChangeRate;    // e.g., -5% from last snapshot

    // For the radar chart by day of the week: Mon, Tue, Wed, etc.
    // Key: dayOfWeek ("monday", "tuesday", ...), Value: count of borrowed
    private Map<String, Integer> borrowedByDayOfWeek;

}