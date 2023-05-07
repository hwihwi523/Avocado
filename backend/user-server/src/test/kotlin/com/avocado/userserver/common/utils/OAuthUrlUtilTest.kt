package com.avocado.userserver.common.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(OAuthUrlUtil::class)
@ExtendWith(SpringExtension::class)
internal class OAuthUrlUtilTest @Autowired constructor(
    private val oAuthUrlUtil: OAuthUrlUtil
) {
    @Test
    fun `url 가져오기 테스트`() {
        println(oAuthUrlUtil.getAuthorizationUrlKakao())
        println(oAuthUrlUtil.getFrontRedirectUrl("aa", "bb", 0))
    }
}