package com.avocado.userserver.api.dto

data class OAuthToken(
    var access_token: String,
    var token_type: String,
    var scope: String,
    var id_token: String
) {

}