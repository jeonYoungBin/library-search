package org.search.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.search.controller.request.SearchRequest;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.service.BookApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {
    private final BookApplicationService bookApplicationService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest searchRequest) {
        return bookApplicationService.search(searchRequest.getQuery(), searchRequest.getPage(), searchRequest.getSize());
    }
}
