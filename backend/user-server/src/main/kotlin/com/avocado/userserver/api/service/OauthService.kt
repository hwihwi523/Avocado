package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.api.dto.OAuthToken
import com.avocado.userserver.api.response.OAuthLoginUrlResp
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.OAuthUrlUtil
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    val oAuthUrlUtil: OAuthUrlUtil
) {
    val log: Logger = LoggerFactory.getLogger(OauthService::class.java)

    suspend fun getOauth2LoginUrl(provider: String): OAuthLoginUrlResp {
        var resp: OAuthLoginUrlResp
        when (provider) {
            "kakao" -> resp = OAuthLoginUrlResp(SocialType.KAKAO, oAuthUrlUtil.getAuthorizationUrlKakao())
            else -> throw BaseException(ResponseCode.BAD_REQUEST)
        }
        return resp
    }

    suspend fun getUserInfoKakao(code: String): KakaoUserInfo {
        var token: OAuthToken = codeToTokenKakao(code)
        return getUserInfoFromToken(token)
    }

    private suspend fun codeToTokenKakao(code: String): OAuthToken {
        log.info("카카오 서버에 유저 정보 요청 시작")
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
        log.info("카카오 서버에서 받은 응답으로 유저 정보 parsing 시작. token: {}", token)
        val idTokenPayload: String = token.id_token.split(".")[1]
        val decodedString = String(Base64.getDecoder().decode(idTokenPayload.toByteArray()))
        log.info("decodedString: {}", decodedString)
        val objectMapper = ObjectMapper().registerKotlinModule()
        return objectMapper.readValue(decodedString, KakaoUserInfo::class.java)
    }
}