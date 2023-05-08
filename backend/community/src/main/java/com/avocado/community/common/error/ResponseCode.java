package com.avocado.community.common.error;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근할 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "자격 증명이 없습니다. Authorization 헤더를 확인해 주세요."),
    WRONG_HEADER(HttpStatus.UNAUTHORIZED, "Bearer Token 을 찾을 수 없습니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근할 수 없는 권한입니다."),
    NOT_FOUND(HttpStatus.BAD_REQUEST, "없는 값입니다."),

    INVALID_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 값입니다."),

    ;

    ResponseCode(HttpStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    HttpStatus status;
    String msg;
}
