package com.avocado.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE"),
    ACCESS_DENIED(403, "ACCESS_DENIED"),
    INVALID_INSERT(500, "INVALID_SAVE"),
    INVALID_UPDATE(500, "INVALID_UPDATE"),
    INVALID_DELETE(500, "INVALID_DELETE"),

    // Consumer
    NO_MEMBER(404, "NO_MEMBER"),

    // Merchandise
    NO_MERCHANDISE(404, "NO_MERCHANDISE"),

    // Review
    EXISTS_REVIEW(400, "EXISTS_REVIEW"),
    NO_REVIEW(404, "NO_REVIEW"),

    // Wishlist
    EXISTS_WISHLIST(400, "EXISTS_WISHLIST"),
    NO_WISHLIST(404, "NO_WISHLIST"),

    // Cart
    EXISTS_CART(400, "EXISTS_CART"),
    NO_CART(404, "NO_CART"),
    ;

    final int status;
    final String message;
}
