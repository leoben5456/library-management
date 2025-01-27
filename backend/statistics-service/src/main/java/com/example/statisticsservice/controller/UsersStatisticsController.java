package com.example.statisticsservice.controller;

import com.example.statisticsservice.model.UsersStatistics;
import com.example.statisticsservice.repository.UserStatisticsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-statistics")
public class UsersStatisticsController {

    private final UserStatisticsRepository repository;

    public UsersStatisticsController(UserStatisticsRepository  repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<UsersStatistics> getAllUserStatistics() {
        return repository.findAll();
    }

    @GetMapping("/latest")
    public UsersStatistics getLatestUserStatistics() {
        return repository.findFirstByOrderByLastUpdatedDesc().orElse(null);
    }
}