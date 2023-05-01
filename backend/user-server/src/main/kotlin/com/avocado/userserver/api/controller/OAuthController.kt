package com.avocado.userserver.api.controller

import com.avocado.userserver.api.response.OAuthLoginUrlResp
import com.avocado.userserver.api.service.ProviderType
import com.avocado.userserver.common.error.BaseException
import com.avocado.userserver.common.error.ResponseCode
import com.avocado.userserver.common.utils.OAuthUrlUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OAuthController(
    val oAuthUrlUtil: OAuthUrlUtil
) {

    @GetMapping(path = ["/consumer/oauth2/{provider}"])
    @ResponseBody
    fun oauth2LoginUrl(
        @PathVariable(name = "provider") provider: String
    ): ResponseEntity<OAuthLoginUrlResp> {

        var resp: OAuthLoginUrlResp
        when (provider) {
            "kakao" -> resp = OAuthLoginUrlResp(ProviderType.KAKAO, oAuthUrlUtil.getAuthorizationUrlKakao())
            else -> throw BaseException(ResponseCode.BAD_REQUEST)
        }
        return ResponseEntity.ok(resp)
    }

    @GetMapping("kakao/redirect")
    fun redirectKakao(@RequestParam code:String) {


//        ServerResponse.ok().render(oAuthUrlUtil.getFrontRedirectUrl(accessToken, refreshToken))
    }

}