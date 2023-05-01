package com.avocado.userserver.api.response

import com.avocado.userserver.db.entity.Provider

data class ProviderLoginResp (
    val accessToken: String,
    val refreshToken: String,
    val email: String,
    val name: String
    ) {

    constructor(accessToken:String, refreshToken:String, provider: Provider): this(accessToken, refreshToken, provider.email, provider.name)
}