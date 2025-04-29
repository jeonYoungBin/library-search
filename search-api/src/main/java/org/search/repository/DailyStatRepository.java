package org.search.repository;

import org.search.entity.DailyStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface DailyStatRepository extends JpaRepository<DailyStat, Long> {
    Long countByQueryAndEventDateTimeBetween(String query, LocalDateTime eventDateTime, LocalDateTime eventDateTime2);

}
