package com.avocado.userserver.api.response

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class TokenRefreshResp (
    val accessToken: String,
    val refreshToken: String,
) {
}