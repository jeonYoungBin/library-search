package org.search.controller.response;

import lombok.Getter;

import java.util.List;

public record PageResult<T>(int page, int size, int totalElements, List<T> contents) {
}
