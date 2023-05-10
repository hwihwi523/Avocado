package com.avocado.payment.exception;

import lombok.Getter;

@Getter
public class NoInventoryException extends RuntimeException {
    ErrorCode errorCode;

    public NoInventoryException(ErrorCode e) {
        super();
        this.errorCode = e;
    }
}
