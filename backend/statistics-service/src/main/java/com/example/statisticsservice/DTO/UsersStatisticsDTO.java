package com.example.statisticsservice.DTO;

import lombok.Data;

import java.util.Map;


@Data
public class UsersStatisticsDTO {
    private int totalUsers;
    private Map<String, Integer> userBreakdown;

}