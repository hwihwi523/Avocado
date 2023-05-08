package com.avocado.commercial.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    //INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    //INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),

    // 403 Forbidden : 유효하지 않은 데이터
    //INVALID_PASSWORD(FORBIDDEN, "비밀번호가 유효하지 않습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    COMMERCIAL_NOT_FOUND(NOT_FOUND, "해당 조건에 맞는 광고를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    //DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
