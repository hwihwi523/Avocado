package com.avocado.userserver.api.service

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(OauthService::class)
@ExtendWith(SpringExtension::class)
internal class OauthServiceTest @Autowired constructor(
    private val oauthService: OauthService
) {
    
//    @Test
//    fun `토큰 발급 테스트`() {
//        runBlocking {
//            println(oauthService.codeToTokenKakao("5_cG8OCKi_W2OcfwDW_pGHGCk7BS2x_zlwAURFjyNhFZMGMcbWLwXViS8imolHpng_iBEQoqJREAAAGH2na6LA"))
//        }
//    }

    @Test
    fun `사용자 정보 가져오기`() {
        runBlocking {
            oauthService.getUserInfoKakao("Wl9OYsZi_XTjejc9S0M5F1WCFhtTsTPiSuier4B_5jh3tJ1bzKWuRLhHbUhaHl76zUmFrwo9c-wAAAGH2newqg")
        }
    }
}