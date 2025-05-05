package org.search.controller.config;

import org.search.library.exception.ErrorType;

public record ErrorResponse(String errMessage, ErrorType errorType) {
}

