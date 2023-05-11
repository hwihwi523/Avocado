package com.avocado.payment.exception;

import lombok.Getter;

@Getter
public class RedirectBusinessLogicException extends RuntimeException {
    ErrorCode errorCode;

    public RedirectBusinessLogicException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
