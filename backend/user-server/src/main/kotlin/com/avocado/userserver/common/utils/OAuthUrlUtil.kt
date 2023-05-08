package com.avocado.userserver.common.utils

import com.avocado.userserver.common.error.BaseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
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
    val FRONT_REDIRECT_URI: String,
    @Value("\${oauth2.kakao.authorization_uri}")
    val AUTHORIZATION_URI_KAKAO: String,
){

    fun getAuthorizationUrlKakao(): String {
        return UriComponentsBuilder.fromUriString(AUTHORIZATION_URI_KAKAO)
            .queryParam("client_id", CLIENT_ID_KAKAO)
            .queryParam("redirect_uri", REDIRECT_URI_KAKAO)
            .queryParam("response_type", "code")
            .build().toUriString()
    }

    fun getFrontRedirectUrl(accessToken: String, refreshToken: String, auth: Int): URI {
        val firstLogin = when (auth) {
            0 -> true
            1 -> false
            else -> throw BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "auth 가 유효하지 않은 값입니다. 관리자에게 문의하세요")
        }

        return UriComponentsBuilder.fromUriString(FRONT_REDIRECT_URI)
            .queryParam("access_token", accessToken)
            .queryParam("refresh_token", refreshToken)
            .queryParam("first_login", firstLogin).build().toUri()
    }


}