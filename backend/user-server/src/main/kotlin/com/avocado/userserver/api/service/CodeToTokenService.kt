package com.avocado.userserver.api.service

import com.avocado.userserver.api.dto.OAuthToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono


@Component
class CodeToTokenService(
    @Value("\${oauth2.kakao.client_id}")
    val CLIENT_ID_KAKAO: String,

    @Value("\${oauth2.kakao.client_secret}")
    val CLIENT_SECRET_KAKAO: String,

    @Value("\${oauth2.kakao.redirect_uri}")
    val REDIRECT_URI_KAKAO: String,

    @Value("\${oauth2.kakao.token_uri}")
    val TOKEN_URI_KAKAO: String
) {

    fun codeToTokenKakao(code: String):OAuthToken {
        val webClient:WebClient = WebClient
            .builder().baseUrl(TOKEN_URI_KAKAO)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
            .build()

        val params = LinkedMultiValueMap<String, String>()
        params["client_id"] = CLIENT_ID_KAKAO
        params["grant_type"] = "authorization_code"
        params["redirect_uri"] = REDIRECT_URI_KAKAO
        params["code"] = code
        params["client_secret"] = CLIENT_SECRET_KAKAO

        val retrieve = webClient.post().body(BodyInserters.fromFormData(params)).retrieve().bodyToMono<String>()
        println(retrieve)
        return OAuthToken("", "", "", 0, "", "")
    }

    fun codeToTokenKakaoTest(code: String?): Mono<OAuthToken?>? {
        val formData: MultiValueMap<String, String> = LinkedMultiValueMap()
        formData.add("client_id", CLIENT_ID_KAKAO)
        formData.add("grant_type", "authorization_code")
        formData.add("redirect_uri", REDIRECT_URI_KAKAO)
        formData.add("code", code)
        formData.add("client_secret", CLIENT_SECRET_KAKAO)
        return WebClient.create(TOKEN_URI_KAKAO)
            .post()
            .uri("/")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData)
            .retrieve()
            .bodyToMono(OAuthToken::class.java)
            .doOnSuccess { token -> println("까까오 아이디 토큰 : $token") }
            .doOnError{ error -> println("$error")}
            .doAfterTerminate{println("멈춤")}
    }




}