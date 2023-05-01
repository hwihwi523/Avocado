package com.avocado.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE"),
    ACCESS_DENIED(403, "ACCESS_DENIED"),

    // Consumer
    NO_MEMBER(404, "NO_MEMBER"),

    // Merchandise
    NO_MERCHANDISE(404, "NO_MERCHANDISE"),

    // Wishlist
    EXISTS_WISHLIST(400, "EXISTS_WISHLIST"),
    NO_WISHLIST(404, "NO_WISHLIST"),

    // Cart
    NO_CART(404, "NO_CART"),
    ;

    final int status;
    final String message;
}
