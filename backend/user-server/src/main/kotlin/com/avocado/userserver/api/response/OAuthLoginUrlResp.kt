package com.avocado.userserver.api.response

import com.avocado.userserver.api.service.ProviderType

data class OAuthLoginUrlResp (
    val provider: ProviderType,
    val url: String
    ) {
}