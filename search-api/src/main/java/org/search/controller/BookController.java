package org.search.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.search.controller.request.SearchRequest;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.service.BookQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/books")
public class BookController {
    private final BookQueryService bookQueryService;

    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest searchRequest) {
        return bookQueryService.search(searchRequest.getQuery(), searchRequest.getPage(), searchRequest.getSize());
    }
}
