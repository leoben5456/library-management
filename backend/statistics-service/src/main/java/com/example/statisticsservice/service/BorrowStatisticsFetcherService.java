package com.example.statisticsservice.service;


import com.example.statisticsservice.DTO.BorrowStatisticsDTO;
import com.example.statisticsservice.model.BorrowStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class BorrowStatisticsFetcherService {

    private final RestTemplate restTemplate;

    @Value("${reservation.service.url}")
    private String reservationServiceUrl;

    public BorrowStatisticsFetcherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BorrowStatistics fetchStatisticsFromReservationService() {
        String url = reservationServiceUrl + "/borrow/stats";
        ResponseEntity<BorrowStatisticsDTO> response = restTemplate.getForEntity(url, BorrowStatisticsDTO.class);

        BorrowStatisticsDTO dto = response.getBody();

        BorrowStatistics statistics = new BorrowStatistics();
        statistics.setDate(LocalDate.now());
        statistics.setTotalBorrowed(dto.getTotalBorrowed());
        statistics.setBorrowedByDayOfWeek(dto.getBorrowedByDayOfWeek());

        return statistics;
    }
}