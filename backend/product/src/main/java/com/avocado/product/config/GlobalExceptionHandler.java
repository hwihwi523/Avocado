package com.avocado.product.config;

import com.avocado.product.dto.response.BaseResp;
import com.avocado.product.exception.AccessDeniedException;
import com.avocado.product.exception.DataManipulationException;
import com.avocado.product.exception.ErrorCode;
import com.avocado.product.exception.InvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 찜 관련 기능 예외 처리
     */
    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<BaseResp> handleWishlistException(InvalidValueException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage()));
    }

    /**
     * 접근 권한 예외 처리
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<BaseResp> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage() + " : 접근 권한이 없습니다."));
    }

    /**
     * 프록시를 사용해 entity 조작 시 발생하는 예외 처리
     */
    @ExceptionHandler(DataManipulationException.class)
    protected ResponseEntity<BaseResp> handleDataManipulationException(DataManipulationException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(BaseResp.of(e.getErrorCode().getMessage()));
    }

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
