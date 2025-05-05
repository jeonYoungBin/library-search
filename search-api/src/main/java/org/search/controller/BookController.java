package org.search.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.search.controller.request.SearchRequest;
import org.search.controller.response.ErrorResponse;
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

    @Operation(summary = "search API", description = "도서 검색결과 제공")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PageResult.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping
    public PageResult<SearchResponse> search(@Valid SearchRequest searchRequest) {
        log.info("[BookController] search={}", searchRequest);
        return bookApplicationService.search(searchRequest.getQuery(), searchRequest.getPage(), searchRequest.getSize());
    }

    @Operation(summary = "stats API", description = "쿼리 통계결과 제공")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = StatResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/stats")
    public StatResponse findQueryCount(@RequestParam(name = "query") String query,
                                       @RequestParam(name = "date") LocalDate date) {
        log.info("[BookController] find stats query={}, date={}", query, date);
        return bookApplicationService.findQueryCount(query, date);
    }
}
