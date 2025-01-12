package com.example.statisticsservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyPenalty {
    private String month; // e.g., "2025-08" or "Aug"
    private int value;    // penalty count or sum of fines


}