package com.avocado.product.dto.request;

import lombok.Getter;

@Getter
public class RemoveCartReq {
    String user_id;
    Long cart_id;
}
