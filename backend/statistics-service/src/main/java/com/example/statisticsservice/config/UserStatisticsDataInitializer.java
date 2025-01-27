package com.example.statisticsservice.config;

import com.example.statisticsservice.model.UsersStatistics;
import com.example.statisticsservice.repository.UserStatisticsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserStatisticsDataInitializer {

    @Bean
    CommandLineRunner initUserStatistics(UserStatisticsRepository  repository) {
        return args -> {
            if (repository.count() == 1) {  // Ensure the database is empty before inserting new data

                Map<String, Integer> breakdown1 = Map.of("Etudiant", 3, "admin", 1);
                Map<String, Integer> breakdown2 = Map.of("Etudiant", 5, "admin", 2);

                repository.save(new UsersStatistics(null, 4, breakdown1, 0, LocalDateTime.now().minusWeeks(4)));
                repository.save(new UsersStatistics(null, 7, breakdown2, 75, LocalDateTime.now().minusWeeks(3)));
                repository.save(new UsersStatistics(null, 10, breakdown2, 42, LocalDateTime.now().minusWeeks(2)));
                repository.save(new UsersStatistics(null, 15, breakdown1, 50, LocalDateTime.now().minusWeeks(1)));
                repository.save(new UsersStatistics(null, 20, breakdown2, 33, LocalDateTime.now()));

                System.out.println("User statistics sample data initialized in MongoDB!");
            }
        };
    }
}
