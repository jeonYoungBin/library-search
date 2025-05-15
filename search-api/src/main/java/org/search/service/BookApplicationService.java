package org.search.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.controller.response.StatResponse;
import org.search.entity.DailyStat;
import org.search.repository.DailyStatRepository;
import org.search.service.event.SearchEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookApplicationService {
    private final BookQueryService bookQueryService;
    private final DailyStatQueryService dailyStatQueryService;
    private final ApplicationEventPublisher eventPublisher;

    public PageResult<SearchResponse> search(String query, int page, int size) {
        PageResult<SearchResponse> response = bookQueryService.search(query, page, size);
        if(!response.contents().isEmpty()) {
            log.info("검색결과 개수 : {}", response.size());
            eventPublisher.publishEvent(new SearchEvent(query, LocalDateTime.now()));
        }
        return response;
    }

    public StatResponse findQueryCount(String query, LocalDate date) {
        return dailyStatQueryService.findQueryCount(query, date);
    }

    public List<StatResponse> findTop5Query() {
        return dailyStatQueryService.findTop5Query();
    }
}
