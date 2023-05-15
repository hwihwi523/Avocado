package com.avocado.search.search.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    CATEGORY_NULL_EXCEPTION(BAD_REQUEST,"category에 입력값이 없습니다"),
    KEYWORD_NULL_EXCEPTIOIN(BAD_REQUEST,"keyword에 입력값이 없습니다"),
    INVALID_CATEGORY(BAD_REQUEST,"카테고리 정보가 올바르지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    PRODUCT_NOT_FOUND(NOT_FOUND, "해당 조건에 맞는 제품을 찾을 수 없습니다."),

    /* 500 Internal Server error : 알 수 없는 에러 */
    UNCOVERED_ERROR(INTERNAL_SERVER_ERROR, "알 수 없는 에러, 관리자에게 문의하세요"),
    UNCOVERED_ELASTIC_SEARCH_ERROR(INTERNAL_SERVER_ERROR, "엘라스틱 서치 에러, 관리자에게 문의하세요")

    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
