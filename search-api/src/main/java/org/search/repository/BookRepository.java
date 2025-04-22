package org.search.repository;

import lombok.RequiredArgsConstructor;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface BookRepository {
    PageResult<SearchResponse> search(String query, int page, int size);
}
