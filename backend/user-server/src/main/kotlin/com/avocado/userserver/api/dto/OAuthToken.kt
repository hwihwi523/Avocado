package com.avocado.userserver.api.dto

data class OAuthToken(
    var access_token: String,
    var token_type: String,
    var refresh_token: String,
    var expires_in: Int,
    var scope: String,
    var refresh_token_expires_in: Int,
    var id_token: String
) {

}