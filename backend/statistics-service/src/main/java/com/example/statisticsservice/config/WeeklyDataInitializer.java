package com.example.statisticsservice.config;

import com.example.statisticsservice.model.BorrowStatistics;
import com.example.statisticsservice.repository.BorrowStatisticsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WeeklyDataInitializer {

    @Bean
    CommandLineRunner initWeeklyData(BorrowStatisticsRepository repository) {
        return args -> {
            if (repository.count() == 0) {  // Ensuring the database is empty before adding new data

                repository.save(new BorrowStatistics(null, LocalDate.of(2024, 1, 7), 200, 0.0, createWeeklyData(50,30,40,20,30,20,10)));
                repository.save(new BorrowStatistics(null, LocalDate.of(2024, 1, 14), 220, 10.0, createWeeklyData(55,35,40,25,35,20,10)));
                repository.save(new BorrowStatistics(null, LocalDate.of(2024, 1, 21), 250, 13.64, createWeeklyData(60,40,50,30,40,20,10)));
                repository.save(new BorrowStatistics(null, LocalDate.of(2024, 1, 28), 240, -4.0, createWeeklyData(55,35,45,30,40,20,15)));
                repository.save(new BorrowStatistics(null, LocalDate.of(2024, 2, 4), 270, 12.5, createWeeklyData(70,45,50,35,45,20,5)));

                System.out.println("Weekly sample data initialized in MongoDB!");
            }
        };
    }

    private Map<String, Integer> createWeeklyData(int mon, int tue, int wed, int thu, int fri, int sat, int sun) {
        Map<String, Integer> weeklyData = new HashMap<>();
        weeklyData.put("monday", mon);
        weeklyData.put("tuesday", tue);
        weeklyData.put("wednesday", wed);
        weeklyData.put("thursday", thu);
        weeklyData.put("friday", fri);
        weeklyData.put("saturday", sat);
        weeklyData.put("sunday", sun);
        return weeklyData;
    }
}