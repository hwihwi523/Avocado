package com.avocado.product.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    private final ErrorCode errorCode;

    public AccessDeniedException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
