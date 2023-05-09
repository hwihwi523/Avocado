package com.avocado.search.search.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class SearchExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { com.avocado.search.search.error.SearchException.class })
    protected ResponseEntity<com.avocado.search.search.error.ErrorResponse> handleCustomException(com.avocado.search.search.error.SearchException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return com.avocado.search.search.error.ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}