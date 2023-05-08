package com.avocado.community.common.error;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.BindException;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    protected ResponseEntity<ErrorResp> handleExpiredJwtException(JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResp(ResponseCode.EXPIRED_TOKEN));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResp> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResp(ResponseCode.INVALID_VALUE));
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResp> handleBindException(BindException e)  {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResp(ResponseCode.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResp> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e)  {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResp(ResponseCode.BAD_REQUEST));
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResp> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResp(ResponseCode.HANDLE_ACCESS_DENIED));
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResp> handleBaseException(BaseException e)  {
        return ResponseEntity.status(e.status)
                .body(new ErrorResp(e.status, e.msg));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResp> handleException(Exception e)  {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResp(ResponseCode.INTERNAL_SERVER_ERROR));
    }

}
