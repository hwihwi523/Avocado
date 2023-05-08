package com.avocado.commercial.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommercialException extends RuntimeException {
    private final ErrorCode errorCode;
}