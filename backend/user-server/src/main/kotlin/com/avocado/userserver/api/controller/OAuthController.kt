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
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse

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
    suspend fun redirectKakao(@RequestParam code:String) {
        val userInfo: KakaoUserInfo = oauthService.getUserInfoKakao(code)
        println(userInfo)
        val consumer: Consumer = consumerService.getConsumerFromSubAndSocial(userInfo.sub, SocialType.KAKAO)
            ?:consumerService.save(userInfo)
        println(consumer)

        val accessToken = jwtProvider.getAccessToken(consumer)
        val refreshToken = jwtProvider.getRefreshToken(consumer)


        ServerResponse.ok().render(oAuthUrlUtil.getFrontRedirectUrl(accessToken, refreshToken))
    }

}