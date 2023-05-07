package com.avocado.userserver.api.controller

import com.avocado.userserver.api.response.OAuthLoginUrlResp
import com.avocado.userserver.api.service.ConsumerService
import com.avocado.userserver.api.service.OauthService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OauthController(
    val oauthService: OauthService,
    val consumerService: ConsumerService,
) {
    val log: Logger = LoggerFactory.getLogger(OauthController::class.java)

    @GetMapping(path = ["/consumer/oauth2/{provider}"])
    @ResponseBody
    suspend fun oauth2LoginUrl(
        @PathVariable(name = "provider") provider: String
    ): ResponseEntity<OAuthLoginUrlResp> {
        log.info("oauth2LoginURL 요청")
        return ResponseEntity.ok(oauthService.getOauth2LoginUrl(provider))
    }

    @GetMapping("kakao/redirect")
    suspend fun redirectKakao(@RequestParam code:String): ResponseEntity<Any> {
        log.info("client 로그인 완료. redirect 요청. code: {}", code)
        val frontRedirectUrl = consumerService.loginOrSignUp(code)
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(frontRedirectUrl).build()
    }

}

