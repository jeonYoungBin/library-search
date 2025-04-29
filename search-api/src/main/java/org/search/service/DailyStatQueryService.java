package org.search.service;

import lombok.RequiredArgsConstructor;
import org.search.controller.response.StatResponse;
import org.search.entity.DailyStat;
import org.search.repository.DailyStatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DailyStatQueryService {
    private final DailyStatRepository dailyStatRepository;

    public StatResponse findQueryCount(String query, LocalDate date) {
        Long count = dailyStatRepository.countByQueryAndEventDateTimeBetween(query,
                date.atStartOfDay(),
                date.atTime(LocalTime.MAX));

        return new StatResponse(query, count);
    }
}
