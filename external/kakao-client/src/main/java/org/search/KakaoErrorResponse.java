package org.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KakaoErrorResponse {
    private String errorType;
    private String message;
}
