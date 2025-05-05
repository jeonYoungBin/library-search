package org.search.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final String errMessage;
    private final ErrorType errorType;
    private final HttpStatus httpStatus;

    public ApiException(String errMessage, ErrorType errorType, HttpStatus httpStatus) {
        this.errMessage = errMessage;
        this.errorType = errorType;
        this.httpStatus = httpStatus;
    }
}
