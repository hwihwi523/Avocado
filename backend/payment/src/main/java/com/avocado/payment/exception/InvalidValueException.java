package com.avocado.payment.exception;

import lombok.Getter;

@Getter
public class InvalidValueException extends RuntimeException {
    private final ErrorCode errorCode;

    public InvalidValueException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
