package com.avocado.userserver.common.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class OAuthUrlUtil(
    @Value("\${oauth2.kakao.redirect_uri}")
    val REDIRECT_URI_KAKAO: String,
    @Value("\${oauth2.kakao.client_id}")
    val CLIENT_ID_KAKAO: String,
    @Value("\${front.redirect_uri}")
    val FRONT_REDIRECT_URI: String
){
    private val AUTHORIZATION_URI_KAKAO: String ="https://kauth.kakao.com/oauth/authorize"

    fun getAuthorizationUrlKakao(): String {
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URI_KAKAO)
            .queryParam("client_id", CLIENT_ID_KAKAO)
            .queryParam("redirect_uri", REDIRECT_URI_KAKAO)
            .queryParam("response_type", "code")
            .build().toUriString()
    }

    fun getFrontRedirectUrl(accessToken: String, refreshToken: String): URI {
        return UriComponentsBuilder.fromUriString(FRONT_REDIRECT_URI)
            .queryParam("access_token", accessToken)
            .queryParam("refresh_token", refreshToken).build().toUri()
    }


}