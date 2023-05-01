package com.avocado.userserver.api.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@WebFluxTest(CodeToTokenService::class)
@ExtendWith(SpringExtension::class)
internal class CodeToTokenServiceTest @Autowired constructor(
    private val codeToTokenService: CodeToTokenService
) {
    
    @Test
    fun `토큰 발급 테스트`() {
        println(codeToTokenService.codeToTokenKakao("ga-M9i4hfdpYxQZ9amSoW35hPfHmoQrwVPzzXuo15IABlG2Y70ekhfya8lMEPDKhp6UqcworDNIAAAGH1y_1-g"))
    }

    @Test
    fun `토큰 발급 테스트2`() {
        println(codeToTokenService.codeToTokenKakaoTest("ibBKU6XIeS679oSN2_yM3n-9KZjI0wVKCcJ0bKkRTt1O78o8LOhiGqYtSyJBS5UaxKzvEwoqJU8AAAGH18Lkqw"))
    }
}