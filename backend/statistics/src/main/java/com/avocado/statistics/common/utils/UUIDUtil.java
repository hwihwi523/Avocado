package com.avocado.statistics.common.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
    public UUID joinByHyphen(String userId) {
        userId = userId.toLowerCase();
        return UUID.fromString(
                userId.substring(0, 8)
                        + "-" + userId.substring(8, 12)
                        + "-" + userId.substring(12, 16)
                        + "-" + userId.substring(16, 20)
                        + "-" + userId.substring(20)
        );
    }

    public static String removeHyphen(UUID userId) {
        return userId.toString().replace("-", "").toLowerCase();
    }
}
