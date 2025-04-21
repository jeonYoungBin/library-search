package org.search.repository;

import lombok.RequiredArgsConstructor;
import org.search.Item;
import org.search.NaverBookResponse;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.feign.NaverClient;
import org.search.util.DateTimeUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NaverBookRepository implements BookRepository {
    private final NaverClient naverClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        NaverBookResponse naverBookResponse = naverClient.searchBook(query, page, size);
        List<SearchResponse> response = naverBookResponse.getItems().stream()
                .map(this::toSearchResponse)
                .collect(Collectors.toList());
        return new PageResult<>(page, size, naverBookResponse.getTotal(), response);
    }

    private SearchResponse toSearchResponse(Item item) {
        return SearchResponse.builder()
                .title(item.getTitle())
                .author(item.getAuthor())
                .publisher(item.getPublisher())
                .isbn(item.getIsbn())
                .pubDate(DateTimeUtils.parse(item.getPubDate()))
                .build();
    }
}
