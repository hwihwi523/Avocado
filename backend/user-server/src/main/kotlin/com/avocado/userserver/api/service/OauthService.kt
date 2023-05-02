package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.api.dto.OAuthToken
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.nio.charset.StandardCharsets
import java.util.*


@Service
class OauthService(
    @Value("\${oauth2.kakao.client_id}")
    val CLIENT_ID_KAKAO: String,

    @Value("\${oauth2.kakao.client_secret}")
    val CLIENT_SECRET_KAKAO: String,

    @Value("\${oauth2.kakao.redirect_uri}")
    val REDIRECT_URI_KAKAO: String,

    @Value("\${oauth2.kakao.token_uri}")
    val TOKEN_URI_KAKAO: String,

) {

    suspend fun getUserInfoKakao(code: String): KakaoUserInfo {
        var token: OAuthToken = codeToTokenKakao(code)
        return getUserInfoFromToken(token)
    }

    suspend fun codeToTokenKakao(code: String): OAuthToken {
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("client_id", CLIENT_ID_KAKAO)
        formData.add("grant_type", "authorization_code")
        formData.add("redirect_uri", REDIRECT_URI_KAKAO)
        formData.add("code", code)
        formData.add("client_secret", CLIENT_SECRET_KAKAO)
        return WebClient.create()
            .post()
            .uri(TOKEN_URI_KAKAO)
            .headers {
                header ->
                header.contentType = MediaType.APPLICATION_FORM_URLENCODED
                header.acceptCharset = Collections.singletonList(StandardCharsets.UTF_8)
            }
            .bodyValue(formData)
            .retrieve()
            .awaitBody()
    }

    private suspend fun getUserInfoFromToken(token: OAuthToken): KakaoUserInfo {
        val idTokenPayload: String = token.id_token.split(".")[1]
        val decodedString = String(Base64.getDecoder().decode(idTokenPayload.toByteArray()))
        println(decodedString)
        val objectMapper = ObjectMapper().registerKotlinModule()
        return objectMapper.readValue(decodedString, KakaoUserInfo::class.java)
    }
}