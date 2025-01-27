package com.example.statisticsservice.controller;

import com.example.statisticsservice.model.BorrowStatistics;
import com.example.statisticsservice.repository.BorrowStatisticsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class BorrowStatisticsController {

    private final BorrowStatisticsRepository repository;

    public BorrowStatisticsController(BorrowStatisticsRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/borrow")
    public List<BorrowStatistics> getAllBorrowStatistics() {
        return repository.findAll();
    }

    @GetMapping("/borrow/latest")
    public BorrowStatistics getLatestBorrowStatistics() {
        return repository.findFirstByOrderByDateDesc().orElse(null);
    }
}