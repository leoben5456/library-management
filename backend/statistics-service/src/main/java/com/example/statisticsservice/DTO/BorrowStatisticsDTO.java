package com.example.statisticsservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowStatisticsDTO {
    private int totalBorrowed;
    private Map<String, Integer> borrowedByDayOfWeek;

}