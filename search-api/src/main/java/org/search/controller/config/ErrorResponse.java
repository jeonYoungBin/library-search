package org.search.controller.config;

import lombok.Getter;
import org.search.library.ErrorType;

public record ErrorResponse(String errMessage, ErrorType errorType) {
}

