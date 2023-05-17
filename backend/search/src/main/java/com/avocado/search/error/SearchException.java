package com.avocado.search.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchException extends RuntimeException {
    private final ErrorCode errorCode;
}