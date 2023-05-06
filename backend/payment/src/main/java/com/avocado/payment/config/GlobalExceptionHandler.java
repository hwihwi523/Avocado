package com.avocado.payment.config;

import com.avocado.payment.dto.response.BaseResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 런타임 예러 처리
     */
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<BaseResp> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResp.of("RUNTIME_ERROR"));
    }

    /**
     * 그 외 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResp> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResp.of("INTERNAL_SERVER_ERROR"));
    }
}
