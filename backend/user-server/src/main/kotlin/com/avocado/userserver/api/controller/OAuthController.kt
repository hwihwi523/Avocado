package com.avocado.userserver.api.controller

import com.avocado.userserver.api.dto.KakaoUserInfo
import com.avocado.userserver.api.response.OAuthLoginUrlResp
import com.avocado.userserver.api.service.ConsumerService
import com.avocado.userserver.api.service.JwtProvider
import com.avocado.userserver.api.service.OauthService
import com.avocado.userserver.api.service.SocialType
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.OAuthUrlUtil
import com.avocado.userserver.db.entity.Consumer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OAuthController(
    val oAuthUrlUtil: OAuthUrlUtil,
    val oauthService: OauthService,
    val consumerService: ConsumerService,
    val jwtProvider: JwtProvider
) {

    @GetMapping(path = ["/consumer/oauth2/{provider}"])
    @ResponseBody
    suspend fun oauth2LoginUrl(
        @PathVariable(name = "provider") provider: String
    ): ResponseEntity<OAuthLoginUrlResp> {

        var resp: OAuthLoginUrlResp
        when (provider) {
            "kakao" -> resp = OAuthLoginUrlResp(SocialType.KAKAO, oAuthUrlUtil.getAuthorizationUrlKakao())
            else -> throw BaseException(ResponseCode.BAD_REQUEST)
        }
        return ResponseEntity.ok(resp)
    }

    @GetMapping("kakao/redirect")
    suspend fun redirectKakao(@RequestParam code:String): ResponseEntity<Any> {
        // 1. 카카오 서버에서 유저 정보 가져오기
        val userInfo: KakaoUserInfo = oauthService.getUserInfoKakao(code)
        println(userInfo)
        
        // 2. consumer DB에 해당 유저 정보가 있는지 확인하고, 없다면 저장하기
        val consumer: Consumer = consumerService.getConsumerFromSubAndSocial(userInfo.sub, SocialType.KAKAO)
            ?:consumerService.save(userInfo)

        val accessToken = jwtProvider.getAccessToken(consumer)
        val refreshToken = jwtProvider.getRefreshToken(consumer)
        println("accessToken: $accessToken, refreshToken: $refreshToken")
        val frontRedirectUrl = oAuthUrlUtil.getFrontRedirectUrl(accessToken, refreshToken)
        println(frontRedirectUrl)

        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).location(frontRedirectUrl).build()
    }

}

