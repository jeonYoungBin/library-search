package org.search.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.search.controller.response.PageResult;
import org.search.controller.response.SearchResponse;
import org.search.repository.BookRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookQueryService {
    private final BookRepository naverBookRepository;
    private final BookRepository kakaoBookRepository;

    public BookQueryService(
            @Qualifier("naverBookRepository") BookRepository naverBookRepository,
            @Qualifier("kakaoBookRepository") BookRepository kakaoBookRepository
    ) {
        this.naverBookRepository = naverBookRepository;
        this.kakaoBookRepository = kakaoBookRepository;
    }

    @CircuitBreaker(name = "naverSearch", fallbackMethod = "searchFallback")
    public PageResult<SearchResponse> search(String query, Integer page, Integer size) {
        log.info("[BookQueryService] search query:{}, page:{}, size:{}", query, page, size);
        return naverBookRepository.search(query, page, size);
    }

    public PageResult<SearchResponse> searchFallback(String query, Integer page, Integer size, Throwable throwable) {
        if(throwable instanceof CallNotPermittedException) {
            return handleOpenCircuit(query, page, size);
        }
        return handleException(query, page, size, throwable);
    }

    private PageResult<SearchResponse> handleOpenCircuit(String query, Integer page, Integer size) {
        log.warn("[BookQueryService] circuit Breaker is open! Fallback to kakao search: {}", query);
        return kakaoBookRepository.search(query, page, size);
    }

    private PageResult<SearchResponse> handleException(String query, Integer page, Integer size, Throwable throwable) {
        log.error("[BookQueryService] An error occurred! Fallback to kakao search: {}", throwable.getMessage());
        return kakaoBookRepository.search(query, page, size);
    }
}
