package com.avocado.statistics.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BaseResp {
    private final String message;
    private final Object data;

    public static BaseResp of(String message) {
        return BaseResp.builder()
                .message(message)
                .build();
    }
    public static BaseResp of(String message, Object data) {
        return BaseResp.builder()
                .message(message)
                .data(data)
                .build();
    }

    @Builder
    public BaseResp(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
