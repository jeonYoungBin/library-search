package org.search.repository;

import lombok.RequiredArgsConstructor;
import org.search.Document;
import org.search.Item;
import org.search.KakaoBookResponse;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.feign.KakaoClient;
import org.search.feign.NaverClient;
import org.search.util.DateTimeUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class KakaoBookRepository implements BookRepository {
    private final KakaoClient kakaoClient;

    @Override
    public PageResult<SearchResponse> search(String query, int page, int size) {
        KakaoBookResponse response = kakaoClient.searchBook(query, page, size);
        List<SearchResponse> responseList = response.getDocuments().stream()
                .map(this::toSearchResponse)
                .collect(Collectors.toList());;
        return new PageResult<>(page, size, response.getMeta().getTotalCount(), responseList);
    }

    private SearchResponse toSearchResponse(Document document) {
        return SearchResponse.builder()
                .title(document.getTitle())
                .author(document.getAuthors().isEmpty() ? "" : document.getAuthors().get(0))
                .publisher(document.getPublisher())
                .isbn(document.getIsbn())
                .pubDate(DateTimeUtils.parseOffsetDateTime(document.getDatetime()).toLocalDate())
                .build();
    }
}
