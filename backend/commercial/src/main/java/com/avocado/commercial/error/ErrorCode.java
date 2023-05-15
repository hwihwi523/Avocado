package com.avocado.commercial.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    BAD_FILE_FORMAT(BAD_REQUEST, "파일 형식을 다시 확인해주세요"),
    BAD_MBTI_ID(BAD_REQUEST,"잘못된 MBTI 식별자입니다"),
    BAD_COMMERCIAL_TYPE(BAD_REQUEST,"잘못된 광고 타입입니다"),
    BAD_PERSONAL_COLOR(BAD_REQUEST,"잘못된 퍼스널 컬러 타입입니다"),
    BAD_AGE(BAD_REQUEST,"잘못된 연령값입니다"),
    BAD_GENDER_TYPE(BAD_REQUEST,"잘못된 성별 식별자입니다(M 또는 F 사용)"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    EXPIRED_TOKEN(UNAUTHORIZED, "사용기한이 만료된 토큰입니다"),

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
