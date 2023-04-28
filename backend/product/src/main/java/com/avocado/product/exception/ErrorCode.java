package com.avocado.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Consumer
    NO_MEMBER(404, "NO_MEMBER"),

    // Merchandise
    NO_MERCHANDISE(404, "NO_MERCHANDISE"),

    // Wishlist
    EXISTS_WISHLIST(400, "EXISTS_WISHLIST"),
    NO_WISHLIST(404, "NO_WISHLIST"),
    ;

    final int status;
    final String message;
}
