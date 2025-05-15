package org.search.service.event;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public record SearchEvent(String query, LocalDateTime timestamp) {
}
