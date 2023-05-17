package com.avocado.userserver.common.error

import org.springframework.http.HttpStatus

enum class ResponseCode(status: HttpStatus, msg: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404 NOT FOUND"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근할 수 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "자격 증명이 없습니다. Authorization 헤더를 확인해 주세요."),

    WRONG_HEADER(HttpStatus.UNAUTHORIZED, "Bearer Token 을 찾을 수 없습니다."),
    WRONG_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    NOT_FOUND_VALUE(HttpStatus.BAD_REQUEST, "없는 값입니다."),

    TYPE_CASTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "변환할 수 없는 자료형입니다."),

    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "로그인 정보가 올바르지 않습니다. 다시 입력해주세요."),
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 값입니다.");

    val status: HttpStatus = status
    val msg: String = msg


}