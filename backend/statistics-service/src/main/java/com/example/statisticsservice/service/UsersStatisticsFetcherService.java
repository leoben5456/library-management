package com.example.statisticsservice.service;

import com.example.statisticsservice.DTO.UsersStatisticsDTO;
import com.example.statisticsservice.model.UsersStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class UsersStatisticsFetcherService {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UsersStatisticsFetcherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsersStatistics fetchUserStatistics() {
        String url = userServiceUrl + "/user-service/user/stats";
        ResponseEntity<UsersStatisticsDTO> response = restTemplate.getForEntity(url, UsersStatisticsDTO.class);

        UsersStatisticsDTO dto = response.getBody();

        UsersStatistics statistics = new UsersStatistics();
        statistics.setTotalUsers(dto.getTotalUsers());
        statistics.setUserBreakdown(dto.getUserBreakdown());
        statistics.setLastUpdated(LocalDateTime.now());

        return statistics;
    }
}