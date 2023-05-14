package com.avocado.product.exception;

import lombok.Getter;

@Getter
public class WishlistException extends RuntimeException {
    private final ErrorCode errorCode;

    public WishlistException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
