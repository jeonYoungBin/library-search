package org.search.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.search.entity.DailyStat;
import org.search.service.DailyStatCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchEventHandler {
    private final DailyStatCommandService dailyStatCommandService;

    @Async
    @EventListener
    public void eventHandler(SearchEvent searchEvent) throws InterruptedException {
        log.info("[SearchEventHandler] handlerEvent: {}", searchEvent);
        DailyStat dailyStat = new DailyStat(searchEvent.query(), searchEvent.timestamp());
        dailyStatCommandService.save(dailyStat);
    }
}
