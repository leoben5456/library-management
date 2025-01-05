package com.example.statisticsservice.controller;


import com.example.statisticsservice.repository.UserStatisticsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class StatisticsController {

    private  final UserStatisticsRepository userStatisticsRepository;

    public StatisticsController(UserStatisticsRepository userStatisticsRepository) {
        this.userStatisticsRepository = userStatisticsRepository;
    }

    @GetMapping("/statistics")
    public String getStatistics() {
        return "Statistics";
    }


    @GetMapping("/statistics/infos")
    public ResponseEntity<?> getStatisticsInfos() {
        return ResponseEntity.ok(userStatisticsRepository.findById("current").orElse(null));
    }

   
}
