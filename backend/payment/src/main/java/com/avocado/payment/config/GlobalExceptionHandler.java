package com.avocado.payment.config;

import com.avocado.payment.dto.response.BaseResp;
import com.avocado.payment.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.view.RedirectView;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity<BaseResp> handleRedirectBusinessLogicException(BusinessLogicException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage()));
    }
    @ExceptionHandler(RedirectBusinessLogicException.class)
    protected RedirectView handleRedirectBusinessLogicException(RedirectBusinessLogicException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        return new RedirectView("http://k8s-avocado-c6c0ca3c6e-209771398.ap-northeast-2.elb.amazonaws.com/FAIL");
    }

    /**
     * 재고 없을 경우 재고 없음 페이지로 이동
     */
    @ExceptionHandler(NoInventoryException.class)
    protected RedirectView handleNoInventoryException(NoInventoryException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        return new RedirectView("http://k8s-avocado-c6c0ca3c6e-209771398.ap-northeast-2.elb.amazonaws.com/NO_INVENTORY");
    }

    /**
     * 유효하지 않은 값으로 발생하는 예외 처리
     */
    @ExceptionHandler(InvalidValueException.class)
    protected ResponseEntity<BaseResp> handleInvalidValueException(InvalidValueException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage()));
    }

    /**
     * 유효하지 않은 값으로 발생하는 예외 처리
     */
    @ExceptionHandler(KakaoPayException.class)
    protected ResponseEntity<BaseResp> handleKakaoPayException(KakaoPayException e) {
        log.error(e.getMessage() + " >> " + e.getErrorCode().getMessage());
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(BaseResp.of(errorCode.getMessage()));
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
