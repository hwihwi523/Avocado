package com.avocado.product.dto.request;

import lombok.Getter;

@Getter
public class AddCartReq {
    String user_id;
    Long merchandise_id;
}
