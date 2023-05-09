package com.avocado.userserver.common.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.net.BindException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResp> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResp(ResponseCode.INVALID_VALUE))
    }

    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): ResponseEntity<ErrorResp> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResp(ResponseCode.BAD_REQUEST))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ResponseEntity<ErrorResp> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResp(ResponseCode.BAD_REQUEST))
    }

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(e: AccessDeniedException): ResponseEntity<ErrorResp> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(ErrorResp(ResponseCode.HANDLE_ACCESS_DENIED))
    }

    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<ErrorResp> {
        return ResponseEntity.status(e.status)
            .body(ErrorResp(e.status, e.msg))
    }

//    @ExceptionHandler(Exception::class)
//    protected fun handleException(e:Exception): ResponseEntity<ErrorResp> {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//            .body(ErrorResp(ResponseCode.INTERNAL_SERVER_ERROR))
//    }


}