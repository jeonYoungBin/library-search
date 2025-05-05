package org.search.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.search.library.ErrorType;

@Schema(description = "에러응답")
public record ErrorResponse(
        @Schema(description = "에러 메시지", example = "잘못된 요청값입니다.")
        String errorMessage,
        @Schema(description = "에러 타입", example = "INVALID_PARAMETER")
        ErrorType errorType) {}
