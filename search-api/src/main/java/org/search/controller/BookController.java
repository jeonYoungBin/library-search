package org.search.controller;

import lombok.RequiredArgsConstructor;
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
    public PageResult<SearchResponse> search(@RequestParam(value = "query")String query,
                                             @RequestParam(value = "page", defaultValue = "1")int page,
                                             @RequestParam(value = "size", defaultValue = "10")int size) {
        return bookQueryService.search(query, size, page);
    }
}
