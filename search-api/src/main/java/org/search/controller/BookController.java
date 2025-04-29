package org.search.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.search.controller.request.SearchRequest;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.controller.response.StatResponse;
import org.search.service.BookApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/books")
public class BookController {
    private final BookApplicationService bookApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest searchRequest) {
        log.info("[BookController] search={}", searchRequest);
        return bookApplicationService.search(searchRequest.getQuery(), searchRequest.getPage(), searchRequest.getSize());
    }

    @GetMapping("/stats")
    public StatResponse findQueryCount(@RequestParam(name = "query") String query,
                                       @RequestParam(name = "date") LocalDate date) {
        log.info("[BookController] find stats query={}, date={}", query, date);
        return bookApplicationService.findQueryCount(query, date);
    }
}
