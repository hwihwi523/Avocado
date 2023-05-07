package com.avocado.product.exception;

import lombok.Getter;

@Getter
public class DataManipulationException extends RuntimeException {
    private final ErrorCode errorCode;

    public DataManipulationException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
