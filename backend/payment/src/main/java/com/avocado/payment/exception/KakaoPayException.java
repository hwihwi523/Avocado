package com.avocado.payment.exception;

import lombok.Getter;

@Getter
public class KakaoPayException extends RuntimeException {
    ErrorCode errorCode;

    public KakaoPayException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
