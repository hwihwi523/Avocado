package com.avocado.userserver.api.response

import com.avocado.userserver.api.service.SocialType
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OAuthLoginUrlResp (
    val provider: SocialType,
    val url: String
    ) {
}