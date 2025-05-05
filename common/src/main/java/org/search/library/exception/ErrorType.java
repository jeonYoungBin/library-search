package org.search.library.exception;

public enum ErrorType {
    EXTERNAL_API_ERROR("외부 API 호출 에러 입니다."),
    UNKNOWN("알 수없는 에러입니다."),
    NO_RESOURCE("존재하지 않는 리소스 입니다."),
    INVALID_PARAMETER("잘못된 요청값입니다.");

    private final String description;

    ErrorType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
