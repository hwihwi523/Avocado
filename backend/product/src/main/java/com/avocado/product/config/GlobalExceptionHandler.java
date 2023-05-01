package com.avocado.product.config;

import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 찜 관련 기능 예외 처리
     */
    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<BaseResp> handleWishlistException(InvalidValueException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage(), null));
    }

    /**
     * 그 외 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResp> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResp.of("INTERNAL_SERVER_ERROR", null));
    }
}
