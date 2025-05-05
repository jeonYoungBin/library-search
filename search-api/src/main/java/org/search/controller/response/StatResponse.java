package org.search.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "검색통계")
public record StatResponse(
        @Schema(description = "쿼리", example = "HTTP")
        String query,
        @Schema(description = "검색횟수", example = "10")
        Long count) {}
