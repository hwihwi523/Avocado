package com.avocado.product.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageResp {
    private final Object content;
    private final Boolean is_last_page;
    private final Long last_merchandise_id;

    public static PageResp of(Object content, Boolean is_last_page, Long last_merchandise_id) {
        return PageResp.builder()
                .content(content)
                .is_last_page(is_last_page)
                .last_merchandise_id(last_merchandise_id)
                .build();
    }

    @Builder
    private PageResp(Object content, Boolean is_last_page, Long last_merchandise_id) {
        this.content = content;
        this.is_last_page = is_last_page;
        this.last_merchandise_id = last_merchandise_id;
    }
}
