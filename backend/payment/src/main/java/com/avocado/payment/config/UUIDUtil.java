package com.avocado.payment.config;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {
    public UUID joinByHyphen(String userId) {
        return UUID.fromString(
                userId.substring(0, 8)
                        + "-" + userId.substring(8, 12)
                        + "-" + userId.substring(12, 16)
                        + "-" + userId.substring(16, 20)
                        + "-" + userId.substring(20)
        );
    }
    public String removeHyphen(UUID uuid) {
        return uuid.toString().replace("-", "");
    }
}
