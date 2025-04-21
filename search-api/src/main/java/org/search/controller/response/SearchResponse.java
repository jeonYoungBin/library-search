package org.search.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record SearchResponse(String title, String author, String publisher, LocalDate pubDate, String isbn) {
}
