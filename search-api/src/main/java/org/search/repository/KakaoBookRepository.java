package org.search.repository;

import lombok.RequiredArgsConstructor;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class KakaoBookRepository implements BookRepository {
    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        return null;
    }
}
