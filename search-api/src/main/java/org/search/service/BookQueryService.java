package org.search.service;

import lombok.RequiredArgsConstructor;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.repository.BookRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookQueryService {
    private final BookRepository bookRepository;
    public PageResult<SearchResponse> search(String query, int page, int size) {
        return bookRepository.search(query, page, size);
    }
}
