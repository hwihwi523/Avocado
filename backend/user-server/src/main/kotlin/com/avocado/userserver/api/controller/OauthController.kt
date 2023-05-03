package com.avocado.userserver.api.controller

import com.avocado.userserver.api.response.OAuthLoginUrlResp
import com.avocado.userserver.api.service.ConsumerService
import com.avocado.userserver.api.service.OauthService
import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.OAuthUrlUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OauthController(
    val oauthService: OauthService,
    val consumerService: ConsumerService,
) {

    @GetMapping(path = ["/consumer/oauth2/{provider}"])
    @ResponseBody
    suspend fun oauth2LoginUrl(
        @PathVariable(name = "provider") provider: String
    ): ResponseEntity<OAuthLoginUrlResp> {
        return ResponseEntity.ok(oauthService.getOauth2LoginUrl(provider))
    }

    @GetMapping("kakao/redirect")
    suspend fun redirectKakao(@RequestParam code:String): ResponseEntity<Any> {
        val frontRedirectUrl = consumerService.loginOrSignUp(code)
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(frontRedirectUrl).build()
    }

}

